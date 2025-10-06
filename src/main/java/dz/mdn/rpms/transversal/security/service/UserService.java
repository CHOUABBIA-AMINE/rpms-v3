package dz.mdn.rpms.transversal.security.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dz.mdn.rpms.transversal.security.domain.dto.UserRequestDTO;
import dz.mdn.rpms.transversal.security.domain.dto.UserResponseDTO;
import dz.mdn.rpms.transversal.security.domain.model.Group;
import dz.mdn.rpms.transversal.security.domain.model.Role;
import dz.mdn.rpms.transversal.security.domain.model.User;
import dz.mdn.rpms.transversal.security.repository.GroupRepository;
import dz.mdn.rpms.transversal.security.repository.RoleRepository;
import dz.mdn.rpms.transversal.security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final GroupRepository groupRepository;
	private final PasswordEncoder passwordEncoder;
 
	@PreAuthorize("hasPermission('USER', 'CREATE')")
	public UserResponseDTO createUser(UserRequestDTO userDto) {
		if (userRepository.existsByUsername(userDto.username())) {
			throw new IllegalArgumentException("Username already exists");
		}
		if (userRepository.existsByEmail(userDto.email())) {
			throw new IllegalArgumentException("Email already exists");
		}

		User user = new User();
		user.setUsername(userDto.username());
		user.setEmail(userDto.email());
		user.setPassword(passwordEncoder.encode(userDto.password()));
     
		setRolesAndGroups(user, userDto.roleIds(), userDto.groupIds());
     
		User savedUser = userRepository.save(user);
		return UserResponseDTO.fromEntity(savedUser);
	}

	@PreAuthorize("hasPermission(#id, 'USER', 'UPDATE')")
	public UserResponseDTO updateUser(Long id, UserRequestDTO userDto) {
		User user = userRepository.findById(id)
								  .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));

		if (!user.getUsername().equals(userDto.username()) && userRepository.existsByUsername(userDto.username())) {
			throw new IllegalArgumentException("Username already exists");
		}
		if (!user.getEmail().equals(userDto.email()) && userRepository.existsByEmail(userDto.email())) {
			throw new IllegalArgumentException("Email already exists");
		}

		user.setUsername(userDto.username());
		user.setEmail(userDto.email());
		if (userDto.password() != null && !userDto.password().isEmpty()) {
			user.setPassword(passwordEncoder.encode(userDto.password()));
		}
     
		setRolesAndGroups(user, userDto.roleIds(), userDto.groupIds());
     
		User updatedUser = userRepository.save(user);
		return UserResponseDTO.fromEntity(updatedUser);
	}

	@PreAuthorize("hasPermission(#id, 'USER', 'DELETE')")
	public void deleteUser(Long id) {
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User not found with id: " + id);
		}
		userRepository.deleteById(id);
	}

	//@PreAuthorize("hasPermission('USER', 'READ')")
	public List<UserResponseDTO> getAllUsers() {
		return userRepository.findAll().stream()
                			 .map(UserResponseDTO::fromEntity)
                			 .toList();
	}

	//@PreAuthorize("hasPermission('USER', 'READ')")
	public Page<UserResponseDTO> getAllUsers(Pageable pageable) {
		return userRepository.findAll(pageable)
							 .map(UserResponseDTO::fromEntity);
	}

	@PreAuthorize("hasPermission(#id, 'USER', 'READ')")
	public UserResponseDTO getUserById(Long id) {
		User user = userRepository.findById(id)
								  .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
		return UserResponseDTO.fromEntity(user);
	}

	private void setRolesAndGroups(User user, Set<Long> roleIds, Set<Long> groupIds) {
		user.getRoles().clear();
		if (roleIds != null && !roleIds.isEmpty()) {
			Set<Role> roles = roleRepository.findAllById(roleIds).stream()
											.collect(Collectors.toSet());
			user.getRoles().addAll(roles);
		}

		user.getGroups().clear();
		if (groupIds != null && !groupIds.isEmpty()) {
			Set<Group> groups = groupRepository.findAllById(groupIds).stream()
											   .collect(Collectors.toSet());
			user.getGroups().addAll(groups);
		}
	}
}