package dz.mdn.rpms.security.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Long expiresIn; // seconds
    private String tokenType;
    private String username;
    private String[] roles;
    private Long id;
}