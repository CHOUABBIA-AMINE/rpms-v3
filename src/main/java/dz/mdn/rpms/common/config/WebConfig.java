package dz.mdn.rpms.common.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dz.mdn.rpms.audit.config.AuditRequestInterceptor;
import dz.mdn.rpms.common.filter.ContentCachingFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private final AuditRequestInterceptor auditInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(auditInterceptor)
                .addPathPatterns("/api/**");
    }

    @Bean
    FilterRegistrationBean<ContentCachingFilter> contentCachingFilter() {
        FilterRegistrationBean<ContentCachingFilter> registration = 
            new FilterRegistrationBean<>();
        registration.setFilter(new ContentCachingFilter());
        registration.addUrlPatterns("/api/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE + 1);
        return registration;
    }
}