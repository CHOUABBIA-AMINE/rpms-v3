package dz.mdn.rpms.core.administration.domain.dto;

import dz.mdn.rpms.core.administration.domain.model.StructureType;

public record StructureTypeResponseDTO(
    Long id,
    String designationAr,
    String designationEn,
    String designationFr,
    String acronymAr,
    String acronymEn,
    String acronymFr
) {
    public static StructureTypeResponseDTO fromEntity(StructureType structureType) {       
        return new StructureTypeResponseDTO(
            structureType.getId(),
            structureType.getDesignationAr(),
            structureType.getDesignationEn(),
            structureType.getDesignationFr(),
            structureType.getAcronymAr(),
            structureType.getAcronymEn(),
            structureType.getAcronymFr()
        );
    }
}