package dz.mdn.rpms.security.domain.dto;

import java.util.Set;
import java.util.stream.Collectors;

import dz.mdn.rpms.security.domain.model.Group;
import dz.mdn.rpms.security.domain.model.Role;
import dz.mdn.rpms.security.domain.model.User;

public record UserResponseDTO(
    Long id,
    String username,
    String email,
    //boolean accountNonExpired,
    //boolean accountNonLocked,
    boolean credentialsNonExpired,
    boolean enabled,
    Set<Long> roleIds,
    Set<Long> groupIds
) {
    public static UserResponseDTO fromEntity(User user) {
        Set<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toSet());
        Set<Long> groupIds = user.getGroups().stream().map(Group::getId).collect(Collectors.toSet());
        
        return new UserResponseDTO(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            //user.isAccountNonExpired(),
            //user.isAccountNonLocked(),
            user.isCredentialsNonExpired(),
            user.isEnabled(),
            roleIds,
            groupIds
        );
    }
}