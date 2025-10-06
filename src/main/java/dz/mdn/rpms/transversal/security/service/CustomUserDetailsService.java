package dz.mdn.rpms.transversal.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dz.mdn.rpms.exception.domain.model.ResourceNotFoundException;
import dz.mdn.rpms.transversal.security.domain.model.User;
import dz.mdn.rpms.transversal.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

	private final UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByUsername(username)
							 .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
	}

	@Transactional(readOnly = true)
	public User getUserById(Long id) {
		return userRepository.findById(id)
							 .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
	}
	
}