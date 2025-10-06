/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: AuditRequestInterceptor
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Class
 *	@Layaer		: Configuration
 *	@Goal		: Audit
 *
 **/

package dz.mdn.rpms.transversal.audit.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import dz.mdn.rpms.transversal.audit.service.LogService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuditRequestInterceptor implements HandlerInterceptor {
    private static final int MAX_PAYLOAD_LENGTH = 10000; // 10KB
    private static final Set<String> EXCLUDED_CONTENT_TYPES = Set.of(
        "multipart/form-data", 
        "application/octet-stream"
    );
    
    private final LogService logService;
    private final Map<String, Long> requestTimestamps = new ConcurrentHashMap<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String requestId = UUID.randomUUID().toString();
        request.setAttribute("requestId", requestId);
        requestTimestamps.put(requestId, System.currentTimeMillis());
        
        // Skip logging for excluded paths/methods
        if (shouldSkipRequest(request)) {
            return true;
        }
        
        // Log basic request info
        logRequestStart(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                              Object handler, Exception ex) {
        String requestId = (String) request.getAttribute("requestId");
        if (requestId == null || shouldSkipRequest(request)) {
            return;
        }

        try {
            long duration = System.currentTimeMillis() - requestTimestamps.remove(requestId);
            Map<String, Object> auditData = buildAuditData(request, response, ex, duration);
            logService.logHttpRequest(ex == null ? "REQUEST_COMPLETED" : "REQUEST_FAILED", auditData);
        } catch (Exception e) {
            log.error("Failed to audit request", e);
        } finally {
            ensureResponseCopy(response);
        }
    }

    private Map<String, Object> buildAuditData(HttpServletRequest request, 
                                             HttpServletResponse response,
                                             Exception ex,
                                             long duration) {
        Map<String, Object> auditData = new LinkedHashMap<>();
        
        // Basic request info
        auditData.put("requestId", request.getAttribute("requestId"));
        auditData.put("method", request.getMethod());
        auditData.put("uri", request.getRequestURI());
        auditData.put("query", request.getQueryString());
        auditData.put("durationMs", duration);
        
        // Response info
        auditData.put("status", response.getStatus());
        auditData.put("responseSize", response.getBufferSize());
        
        // Client info
        auditData.put("clientIp", getClientIp(request));
        auditData.put("userAgent", request.getHeader("User-Agent"));
        auditData.put("referer", request.getHeader("Referer"));
        
        // Security context
        addSecurityContext(auditData);
        
        // Headers (filtered)
        auditData.put("headers", getFilteredHeaders(request));
        
        // Body content (if applicable)
        addBodyContent(request, response, auditData);
        
        // Exception info
        if (ex != null) {
            auditData.put("error", ex.getMessage());
            auditData.put("errorType", ex.getClass().getSimpleName());
        }
        
        return auditData;
    }

    private void addSecurityContext(Map<String, Object> auditData) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            Map<String, Object> securityContext = new HashMap<>();
            securityContext.put("username", authentication.getName());
            securityContext.put("authorities", authentication.getAuthorities());
            securityContext.put("credentials", authentication.getCredentials() != null ? "[PROTECTED]" : null);
            auditData.put("securityContext", securityContext);
        }
    }

    private void addBodyContent(HttpServletRequest request, HttpServletResponse response, 
                              Map<String, Object> auditData) {
        if (shouldCaptureBody(request)) {
            ContentCachingRequestWrapper wrappedRequest = 
                WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
            ContentCachingResponseWrapper wrappedResponse = 
                WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
            
            if (wrappedRequest != null) {
                auditData.put("requestBody", getContentAsString(
                    wrappedRequest.getContentAsByteArray(),
                    request.getCharacterEncoding()
                ));
            }
            
            if (wrappedResponse != null) {
                auditData.put("responseBody", getContentAsString(
                    wrappedResponse.getContentAsByteArray(),
                    response.getCharacterEncoding()
                ));
            }
        }
    }

    private String getContentAsString(byte[] buf, String charsetName) {
        if (buf == null || buf.length == 0) return null;
        
        int length = Math.min(buf.length, MAX_PAYLOAD_LENGTH);
        try {
            return new String(buf, 0, length, charsetName);
        } catch (UnsupportedEncodingException e) {
            return "Unsupported encoding";
        }
    }

    private boolean shouldCaptureBody(HttpServletRequest request) {
        String contentType = request.getContentType();
        return contentType != null &&
               !EXCLUDED_CONTENT_TYPES.stream().anyMatch(contentType::startsWith) &&
               Arrays.asList(HttpMethod.POST.name(), 
                           HttpMethod.PUT.name(), 
                           HttpMethod.PATCH.name())
                    .contains(request.getMethod());
    }

    private Map<String, String> getFilteredHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            String headerValue = request.getHeader(headerName);
            
            // Mask sensitive headers
            if (isSensitiveHeader(headerName)) {
                headers.put(headerName, "[PROTECTED]");
            } else {
                headers.put(headerName, headerValue);
            }
        }
        
        return headers;
    }

    private boolean isSensitiveHeader(String headerName) {
        String lowerHeader = headerName.toLowerCase();
        return lowerHeader.contains("auth") || 
               lowerHeader.contains("cookie") ||
               lowerHeader.contains("token") ||
               lowerHeader.contains("secret");
    }

    private String getClientIp(HttpServletRequest request) {
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

    private void ensureResponseCopy(HttpServletResponse response) {
        ContentCachingResponseWrapper wrapper = 
            WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            try {
                wrapper.copyBodyToResponse();
            } catch (IOException e) {
                log.warn("Failed to copy response body", e);
            }
        }
    }

    private boolean shouldSkipRequest(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator") ||
               request.getRequestURI().startsWith("/swagger") ||
               request.getRequestURI().startsWith("/v3/api-docs") ||
               "OPTIONS".equals(request.getMethod());
    }

    private void logRequestStart(HttpServletRequest request) {
        if (log.isDebugEnabled()) {
            log.debug("Request Started: {} {} from {}", 
                request.getMethod(), 
                request.getRequestURI(),
                getClientIp(request));
        }
    }
}