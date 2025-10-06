package dz.mdn.rpms.transversal.security.domain.dto;

import dz.mdn.rpms.transversal.security.domain.model.Permission;

public record PermissionResponseDTO(
    Long id,
    String name,
    String description,
    Long authorityId,
    String authorityName
) {
    public static PermissionResponseDTO fromEntity(Permission permission) {
        return new PermissionResponseDTO(
            permission.getId(),
            permission.getName(),
            permission.getDescription(),
            permission.getAuthority() != null ? permission.getAuthority().getId() : null,
            permission.getAuthority() != null ? permission.getAuthority().getName() : null
        );
    }
}