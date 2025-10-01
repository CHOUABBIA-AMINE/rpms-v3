package dz.mdn.rpms.core.administration.domain.dto;

public record StructureTypeDTO(
    Long id,
    String designationAr,
    String designationEn,
    String designationFr,
    String acronymAr,
    String acronymEn,
    String acronymFr
) {}