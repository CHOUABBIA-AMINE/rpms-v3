package dz.mdn.rpms.security.domain.dto;

import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @NotBlank @Size(max = 30) 
    String username,
    
    @NotBlank @Email @Size(max = 100) 
    String email,
    
    @NotBlank @Size(max = 120) 
    String password,
    
    Set<Long> roleIds,
    
    Set<Long> groupIds
) {}