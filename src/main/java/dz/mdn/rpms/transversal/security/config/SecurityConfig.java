package dz.mdn.rpms.transversal.security.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.Ordered;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import dz.mdn.rpms.exception.controller.GlobalExceptionHandler;
import dz.mdn.rpms.transversal.security.filter.JwtAuthenticationFilter;
import dz.mdn.rpms.transversal.security.filter.RateLimitingFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
    private final GlobalExceptionHandler globalExceptionHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RateLimitingFilter rateLimitingFilter;
    //private final JwtLogoutHandler jwtLogoutHandler;
    
    @Value("${app.cors.allowed-origins:http://localhost:3000,http://localhost:4200}")
    private String allowedOrigins;
    
    @Value("${app.cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String allowedMethods;
    
    @Value("${app.cors.max-age:3600}")
    private Long corsMaxAge;
    
    @Value("${server.servlet.context-path:/rpmsAPI}")
    private String contextPath;
    
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                
                .exceptionHandling(exception -> exception
            	    .authenticationEntryPoint((request, response, authException) -> {
            	        globalExceptionHandler.handleAuthentication(authException, request, response);
            	    })
            	    .accessDeniedHandler((request, response, accessDeniedException) -> {
            	        globalExceptionHandler.handleAccessDenied(accessDeniedException, request, response);
            	    })
            	)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                									 .maximumSessions(1)
                									 .maxSessionsPreventsLogin(false))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers(contextPath + "/auth/**", contextPath + "/public/**").permitAll()
                    .requestMatchers("/actuator/health", "/actuator/info").permitAll()
                    //.requestMatchers("/smsAPI/public/**").permitAll()
                    //.requestMatchers("/smsAPI/**").permitAll()
                    .anyRequest().authenticated()
                )
                .logout(logout -> logout
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(rateLimitingFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
    
    @Bean
    MethodSecurityExpressionHandler methodSecurityExpressionHandler(PermissionEvaluator permissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }
    
//    @Bean
//	FilterRegistrationBean<CorsFilter> rmsCorsFilter() {
//	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//	    CorsConfiguration config = new CorsConfiguration();
//	    config.setAllowCredentials(true);
//	    config.setAllowedOriginPatterns(Collections.singletonList("*"));
//	    //config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
//	    config.setAllowedMethods(Collections.singletonList("*"));
//	    config.setAllowedHeaders(Collections.singletonList("*"));
//	    source.registerCorsConfiguration("/**", config);
//	    FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(new CorsFilter(source));
//	    filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
//	    return filter;
//	}
    
    @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Security-focused CORS configuration
        config.setAllowCredentials(true);
        
        // Specific allowed origins (not wildcard for security)
        List<String> origins = Arrays.asList(allowedOrigins.split(","));
        config.setAllowedOriginPatterns(origins);
        
        // Specific allowed methods
        List<String> methods = Arrays.asList(allowedMethods.split(","));
        config.setAllowedMethods(methods);
        
        // Specific allowed headers
        config.setAllowedHeaders(Arrays.asList(
            "Authorization",
            "Cache-Control",
            "Content-Type",
            "X-Requested-With",
            "Accept",
            "Origin",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));
        
        // Exposed headers
        config.setExposedHeaders(Arrays.asList(
            "Authorization",
            "X-Total-Count",
            "X-Page-Number",
            "X-Page-Size"
        ));
        
        // Cache preflight requests
        config.setMaxAge(corsMaxAge);
        
        source.registerCorsConfiguration("/**", config);
        
        FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(new CorsFilter(source));
        filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
        filter.setName("corsFilter");
        
        return filter;
    }
    
    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        String authorities = event.getAuthentication().getAuthorities().toString();
        log.info("Successful authentication for user: {} with authorities: {}", username, authorities);
    }

}