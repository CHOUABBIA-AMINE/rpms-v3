package dz.mdn.rpms.transversal.security.domain.dto;

import java.util.Set;
import java.util.stream.Collectors;

import dz.mdn.rpms.transversal.security.domain.model.Permission;
import dz.mdn.rpms.transversal.security.domain.model.Role;

public record RoleResponseDTO(
    Long id,
    String name,
    String description,
    Set<Long> permissionIds
) {
    public static RoleResponseDTO fromEntity(Role role) {
        Set<Long> permissionIds = role.getPermissions().stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());
        
        return new RoleResponseDTO(
            role.getId(),
            role.getName(),
            role.getDescription(),
            permissionIds
        );
    }
}