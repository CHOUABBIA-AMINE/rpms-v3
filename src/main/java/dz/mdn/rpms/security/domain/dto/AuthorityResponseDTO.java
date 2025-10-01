package dz.mdn.rpms.security.domain.dto;

import java.util.Set;
import java.util.stream.Collectors;

import dz.mdn.rpms.security.domain.model.Authority;
import dz.mdn.rpms.security.domain.model.Permission;

public record AuthorityResponseDTO(
    Long id,
    String name,
    String description,
    Set<Long> permissionIds
) {
    public static AuthorityResponseDTO fromEntity(Authority authority) {
        Set<Long> permissionIds = authority.getPermissions().stream()
                .map(Permission::getId)
                .collect(Collectors.toSet());
        
        return new AuthorityResponseDTO(
            authority.getId(),
            authority.getName(),
            authority.getDescription(),
            permissionIds
        );
    }
}