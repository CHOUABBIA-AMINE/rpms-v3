package dz.mdn.rpms.core.administration.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record StructureTypeRequestDTO(
    @NotBlank @Size(max = 100) String designationAr,
    @NotBlank @Size(max = 100) String designationEn,
    @NotBlank @Size(max = 100) String designationFr,
    @Size(max = 500) String acronymAr,
    @Size(max = 500) String acronymEn,
    @Size(max = 500) String acronymFr
) {}