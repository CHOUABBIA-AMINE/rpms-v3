/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: AuditConfig
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Class
 *	@Layaer		: Configuration
 *	@Goal		: Audit
 *
 **/

package dz.mdn.rpms.transversal.audit.config;

import java.util.Optional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Configuration
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class AuditConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return () -> Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(authentication -> {
                    if (authentication.getPrincipal() instanceof UserDetails) {
                        return ((UserDetails) authentication.getPrincipal()).getUsername();
                    }
                    return authentication.getName();
                })
                .or(() -> Optional.of("SYSTEM"));
    }
}