package dz.mdn.rpms.security.domain.dto;

import java.util.Set;
import java.util.stream.Collectors;

import dz.mdn.rpms.security.domain.model.Group;
import dz.mdn.rpms.security.domain.model.Role;
import dz.mdn.rpms.security.domain.model.User;

public record GroupResponseDTO(
    Long id,
    String name,
    String description,
    Set<Long> roleIds,
    Set<Long> userIds
) {
    public static GroupResponseDTO fromEntity(Group group) {
        Set<Long> roleIds = group.getRoles().stream()
                .map(Role::getId)
                .collect(Collectors.toSet());
        
        Set<Long> userIds = group.getUsers().stream()
                .map(User::getId)
                .collect(Collectors.toSet());
        
        return new GroupResponseDTO(
            group.getId(),
            group.getName(),
            group.getDescription(),
            roleIds,
            userIds
        );
    }
}