package dz.mdn.rpms.transversal.security.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthorityRequestDTO(
    @NotBlank @Size(max = 50) String name,
    @Size(max = 200) String description
) {}