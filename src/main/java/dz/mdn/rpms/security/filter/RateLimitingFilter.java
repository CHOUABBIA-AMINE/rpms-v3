package dz.mdn.rpms.security.filter;

import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dz.mdn.rpms.exception.domain.model.RateLimitExceededException;
import dz.mdn.rpms.security.service.RateLimitingService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RateLimitingFilter extends OncePerRequestFilter {
    private final RateLimitingService rateLimitingService;

    public RateLimitingFilter(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain filterChain) 
            throws ServletException, IOException {
        String clientId = getClientIdentifier(request);
        try {
            rateLimitingService.checkRateLimit(clientId);
            filterChain.doFilter(request, response);
        } catch (RateLimitExceededException e) {
            response.setStatus(429);
            response.getWriter().write("Too many requests");
            response.setContentType("application/json");
            response.getWriter().write(
                String.format("{\"error\":\"Too many requests\",\"message\":\"%s\"}", e.getMessage())
            );
        }
    }

    private String getClientIdentifier(HttpServletRequest request) {
        // Prioritize API key, then session, then IP
        String apiKey = request.getHeader("X-API-KEY");
        if (apiKey != null && !apiKey.isBlank()) {
            return "api_key:" + apiKey;
        }
        
        String sessionId = request.getSession(false) != null ? 
                          request.getSession().getId() : null;
        if (sessionId != null) {
            return "session:" + sessionId;
        }
        
        return "ip:" + request.getRemoteAddr();
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        // Exclude health checks and authentication endpoints
        return path.startsWith("/actuator/health") || 
               path.startsWith("/api/auth");
    }
}