/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: CustomAuditorAware
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Class
 *	@Layaer		: Service
 *	@Goal		: Audit
 *
 **/

package dz.mdn.rpms.transversal.audit.service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dz.mdn.rpms.transversal.audit.domain.model.AuditEntry;
import dz.mdn.rpms.transversal.audit.repository.AuditRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuditService {
    private final AuditRepository auditRepository;
    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;
    
    // Cache to track request timings
    private final Map<String, StopWatch> requestTimers = new ConcurrentHashMap<>();
    
    // List of sensitive fields to mask
    private static final Set<String> SENSITIVE_FIELDS = Set.of(
        "password", "token", "secret", "authorization", 
        "creditCard", "ssn", "cvv"
    );
    
    // List of headers to mask
    private static final Set<String> SENSITIVE_HEADERS = Set.of(
        "authorization", "cookie", "set-cookie"
    );

    @Async
    public void logEvent(String action, String resourceType, Long resourceId, Object requestDetails) {
        try {
            AuditEntry entry = buildAuditEntry(action, resourceType, resourceId, requestDetails);
            auditRepository.save(entry);
            log.debug("Audit log saved: {}", entry);
        } catch (Exception e) {
            log.error("Failed to save audit entry", e);
        }
    }

    @Async
    public void logHttpRequest(String action, Map<String, Object> requestData) {
        try {
            AuditEntry entry = buildHttpAuditEntry(action, requestData);
            auditRepository.save(entry);
        } catch (Exception e) {
            log.error("Failed to save HTTP audit entry", e);
        }
    }

    public void startRequestTimer(String requestId) {
        StopWatch timer = new StopWatch();
        timer.start();
        requestTimers.put(requestId, timer);
    }

    public void stopAndLogRequestTimer(String requestId, String action) {
        StopWatch timer = requestTimers.remove(requestId);
        if (timer != null) {
            timer.stop();
            logEvent(action, "REQUEST_TIMING", null, 
                Map.of(
                    "durationMs", timer.getTotalTimeMillis(),
                    "requestId", requestId
                ));
        }
    }

    private AuditEntry buildAuditEntry(String action, String resourceType, 
                                     Long resourceId, Object details) {
        return AuditEntry.builder()
            .action(action)
            .resourceType(resourceType)
            .resourceId(resourceId)
            .requestDetails(maskSensitiveData(serialize(details)))
            .httpMethod(getCurrentHttpMethod())
            .endpoint(getCurrentEndpoint())
            .performedBy(getCurrentUsername())
            .actionTime(Instant.now())
            .clientIp(getClientIp())
            .userAgent(getUserAgent())
            .build();
    }

    private AuditEntry buildHttpAuditEntry(String action, Map<String, Object> requestData) {
        return AuditEntry.builder()
            .action(action)
            .resourceType("HTTP_REQUEST")
            .requestDetails(maskSensitiveData(serialize(requestData)))
            .httpMethod((String) requestData.get("method"))
            .endpoint((String) requestData.get("uri"))
            .performedBy((String) requestData.getOrDefault("username", "SYSTEM"))
            .actionTime(Instant.now())
            .clientIp((String) requestData.get("clientIp"))
            .userAgent((String) requestData.get("userAgent"))
            .build();
    }

    private String serialize(Object object) {
        if (object == null) return null;
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("Failed to serialize object for auditing", e);
            return "serialization-error";
        }
    }

    private String maskSensitiveData(String json) {
        if (json == null) return null;
        
        try {
            // Mask sensitive fields in JSON
            for (String field : SENSITIVE_FIELDS) {
                json = json.replaceAll(
                    "(?i)\"" + field + "\":\"[^\"]*\"", 
                    "\"" + field + "\":\"*****\"");
            }
            
            // Mask sensitive headers
            for (String header : SENSITIVE_HEADERS) {
                json = json.replaceAll(
                    "(?i)\"" + header + "\":\"[^\"]*\"", 
                    "\"" + header + "\":\"*****\"");
            }
            
            return json;
        } catch (Exception e) {
            log.warn("Failed to mask sensitive data", e);
            return json;
        }
    }

    private String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null ? authentication.getName() : "ANONYMOUS";
    }

    private String getCurrentHttpMethod() {
        return request != null ? request.getMethod() : null;
    }

    private String getCurrentEndpoint() {
        return request != null ? request.getRequestURI() : null;
    }

    private String getClientIp() {
        if (request == null) return null;
        
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    private String getUserAgent() {
        return request != null ? request.getHeader("User-Agent") : null;
    }

    // Batch insert for high-volume systems
    @Async
    public void bulkInsert(List<AuditEntry> entries) {
        try {
            auditRepository.saveAll(entries);
        } catch (Exception e) {
            log.error("Failed to bulk insert audit entries", e);
        }
    }

    // Cleanup old audit logs
    public void cleanupOldEntries(Instant cutoffDate) {
        try {
            auditRepository.deleteByActionTimeBefore(cutoffDate);
        } catch (Exception e) {
            log.error("Failed to cleanup old audit entries", e);
        }
    }
}