package dz.mdn.rpms.exception.domain.dto;

import lombok.Data;

@Data
public class ValidationError {
    private String field;
    private String message;
    private Object rejectedValue;
}