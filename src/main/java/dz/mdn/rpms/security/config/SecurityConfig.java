package dz.mdn.rpms.security.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
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
import dz.mdn.rpms.security.filter.JwtAuthenticationFilter;
import dz.mdn.rpms.security.filter.RateLimitingFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
    @Autowired
    private GlobalExceptionHandler globalExceptionHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final RateLimitingFilter rateLimitingFilter;
    
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
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/smsAPI/auth/**").permitAll()
                    .requestMatchers("/smsAPI/public/**").permitAll()
                    .requestMatchers("/smsAPI/**").permitAll()
                    .anyRequest().permitAll())//.authenticated())
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
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    MethodSecurityExpressionHandler methodSecurityExpressionHandler(PermissionEvaluator permissionEvaluator) {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        return expressionHandler;
    }
    
    @Bean
	FilterRegistrationBean<CorsFilter> rmsCorsFilter() {
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	    CorsConfiguration config = new CorsConfiguration();
	    config.setAllowCredentials(true);
	    config.setAllowedOriginPatterns(Collections.singletonList("*"));
	    //config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
	    config.setAllowedMethods(Collections.singletonList("*"));
	    config.setAllowedHeaders(Collections.singletonList("*"));
	    source.registerCorsConfiguration("/**", config);
	    FilterRegistrationBean<CorsFilter> filter = new FilterRegistrationBean<>(new CorsFilter(source));
	    filter.setOrder(Ordered.HIGHEST_PRECEDENCE);
	    return filter;
	}
}