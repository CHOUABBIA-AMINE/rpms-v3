package dz.mdn.rpms.security.domain.dto;

import java.util.Set;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RoleRequestDTO(
    @NotBlank @Size(max = 50) String name,
    @Size(max = 200) String description,
    Set<Long> permissionIds
) {}