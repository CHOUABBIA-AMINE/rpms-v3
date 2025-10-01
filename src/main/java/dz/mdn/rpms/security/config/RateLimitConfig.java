package dz.mdn.rpms.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import dz.mdn.rpms.security.filter.RateLimitingFilter;
import dz.mdn.rpms.security.service.RateLimitingService;

@Configuration
@EnableScheduling
public class RateLimitConfig {

    private final RateLimitingService rateLimitingService;

    public RateLimitConfig(RateLimitingService rateLimitingService) {
        this.rateLimitingService = rateLimitingService;
    }

    @Bean
    RateLimitingFilter rateLimitingFilter(RateLimitingService rateLimitingService) {
        return new RateLimitingFilter(rateLimitingService);
    }

    @Scheduled(fixedRate = 3600000) // Run every hour
    public void cleanUpStaleBuckets() {
        rateLimitingService.cleanUpStaleBuckets();
    }
}