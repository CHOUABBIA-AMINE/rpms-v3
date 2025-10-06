package dz.mdn.rpms.transversal.security.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dz.mdn.rpms.exception.domain.model.TokenRefreshException;
import dz.mdn.rpms.transversal.security.domain.dto.AuthRequest;
import dz.mdn.rpms.transversal.security.domain.dto.AuthResponse;
import dz.mdn.rpms.transversal.security.domain.dto.RefreshTokenRequest;
import dz.mdn.rpms.transversal.security.domain.model.Role;
import dz.mdn.rpms.transversal.security.domain.model.User;
import dz.mdn.rpms.transversal.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse authenticate(AuthRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
            )
        );
        
        var user = userRepository.findByUsername(request.getUsername())
            .orElseThrow();
        return generateAuthResponse(user);
    }

    public AuthResponse refreshToken(RefreshTokenRequest request) {
        String refreshToken = request.getRefreshToken();
        
        if (!jwtService.isRefreshToken(refreshToken)) {
            throw new TokenRefreshException("Invalid refresh token type");
        }
        
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        
        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new TokenRefreshException("Refresh token expired or invalid");
        }
        
        return generateAuthResponse(user);
    }

    private AuthResponse generateAuthResponse(User user) {
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        //String [] roleNames = user.getRoles().stream().map(Role::getName).toArray(String[]::new);
        		//user.getRoles().stream().map(role -> role.getName()).toArray(String[]);
        return AuthResponse.builder()
            .accessToken(accessToken)
            .refreshToken(refreshToken)
            .expiresIn(jwtService.getAccessTokenExpiration() / 1000) // Convert to seconds
            .tokenType("Bearer")
            .username(user.getUsername())
            .id(user.getId())
            .roles(user.getRoles().stream().map(Role::getName).toArray(String[]::new))
            .build();
    }
}