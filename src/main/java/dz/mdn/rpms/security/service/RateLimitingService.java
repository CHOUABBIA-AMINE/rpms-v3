package dz.mdn.rpms.security.service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import dz.mdn.rpms.exception.domain.model.RateLimitExceededException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.ConsumptionProbe;

@Service
public class RateLimitingService {
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();
    private final Map<String, Long> lastAccessTimes = new ConcurrentHashMap<>();
    private static final long STALE_TIMEOUT_MS = 3600000; // 1 hour

    public Bucket resolveBucket(String key) {
        lastAccessTimes.put(key, System.currentTimeMillis());
        return cache.computeIfAbsent(key, k -> createNewBucket());
    }

    private Bucket createNewBucket() {
        Bandwidth limit = Bandwidth.builder()
                .capacity(10)
                .refillIntervally(10, Duration.ofMinutes(1))
                .build();
        
        return Bucket.builder()
                .addLimit(limit)
                .build();
    }

    public void checkRateLimit(String key) {
        Bucket bucket = resolveBucket(key);
        ConsumptionProbe probe = bucket.tryConsumeAndReturnRemaining(1);
        
        if (!probe.isConsumed()) {
            throw new RateLimitExceededException(
                String.format("API rate limit exceeded. Try again in %d seconds", 
                probe.getNanosToWaitForRefill() / 1_000_000_000)
            );
        }
    }

    public void cleanUpStaleBuckets() {
        long now = System.currentTimeMillis();
        cache.keySet().removeIf(key -> {
            Long lastAccess = lastAccessTimes.get(key);
            if (lastAccess == null || (now - lastAccess) > STALE_TIMEOUT_MS) {
                lastAccessTimes.remove(key);
                return true;
            }
            return false;
        });
    }
}