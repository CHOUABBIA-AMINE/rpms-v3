package dz.mdn.rpms.security.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record PermissionRequestDTO(
    @NotBlank @Size(max = 50) String name,
    @Size(max = 200) String description,
    Long authorityId
) {}