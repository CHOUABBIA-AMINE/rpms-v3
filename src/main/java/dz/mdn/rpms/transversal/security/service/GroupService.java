package dz.mdn.rpms.transversal.security.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import dz.mdn.rpms.transversal.security.domain.dto.GroupRequestDTO;
import dz.mdn.rpms.transversal.security.domain.dto.GroupResponseDTO;
import dz.mdn.rpms.transversal.security.domain.model.Group;
import dz.mdn.rpms.transversal.security.domain.model.Role;
import dz.mdn.rpms.transversal.security.domain.model.User;
import dz.mdn.rpms.transversal.security.repository.GroupRepository;
import dz.mdn.rpms.transversal.security.repository.RoleRepository;
import dz.mdn.rpms.transversal.security.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepository groupRepository;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @PreAuthorize("hasPermission('GROUP', 'CREATE')")
    public GroupResponseDTO createGroup(GroupRequestDTO groupDto) {
        if (groupRepository.existsByName(groupDto.name())) {
            throw new IllegalArgumentException("Group name already exists");
        }

        Group group = new Group();
        group.setName(groupDto.name());
        group.setDescription(groupDto.description());
        
        setRoles(group, groupDto.roleIds());
        
        Group savedGroup = groupRepository.save(group);
        return GroupResponseDTO.fromEntity(savedGroup);
    }

    @PreAuthorize("hasPermission(#id, 'GROUP', 'READ')")
    public GroupResponseDTO getGroupById(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
        return GroupResponseDTO.fromEntity(group);
    }

    @PreAuthorize("hasPermission(#id, 'GROUP', 'UPDATE')")
    public GroupResponseDTO updateGroup(Long id, GroupRequestDTO groupDto) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));

        if (!group.getName().equals(groupDto.name()) && groupRepository.existsByName(groupDto.name())) {
            throw new IllegalArgumentException("Group name already exists");
        }

        group.setName(groupDto.name());
        group.setDescription(groupDto.description());
        
        setRoles(group, groupDto.roleIds());
        
        Group updatedGroup = groupRepository.save(group);
        return GroupResponseDTO.fromEntity(updatedGroup);
    }

    @PreAuthorize("hasPermission(#id, 'GROUP', 'DELETE')")
    public void deleteGroup(Long id) {
        Group group = groupRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + id));
        
        // Check if group has users before deletion
        if (!group.getUsers().isEmpty()) {
            throw new IllegalStateException("Cannot delete group with assigned users");
        }
        
        groupRepository.delete(group);
    }

    @PreAuthorize("hasPermission('GROUP', 'READ')")
    public Page<GroupResponseDTO> getAllGroups(Pageable pageable) {
        return groupRepository.findAll(pageable)
                .map(GroupResponseDTO::fromEntity);
    }

    @PreAuthorize("hasPermission(#groupId, 'GROUP', 'UPDATE')")
    public GroupResponseDTO addUserToGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
        group.getUsers().add(user);
        user.getGroups().add(group);
        
        groupRepository.save(group);
        return GroupResponseDTO.fromEntity(group);
    }

    @PreAuthorize("hasPermission(#groupId, 'GROUP', 'UPDATE')")
    public GroupResponseDTO removeUserFromGroup(Long groupId, Long userId) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new EntityNotFoundException("Group not found with id: " + groupId));
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
        
        group.getUsers().remove(user);
        user.getGroups().remove(group);
        
        groupRepository.save(group);
        return GroupResponseDTO.fromEntity(group);
    }

    private void setRoles(Group group, Set<Long> roleIds) {
        group.getRoles().clear();
        if (roleIds != null && !roleIds.isEmpty()) {
            Set<Role> roles = roleRepository.findAllById(roleIds).stream()
                    .collect(Collectors.toSet());
            group.getRoles().addAll(roles);
        }
    }
}