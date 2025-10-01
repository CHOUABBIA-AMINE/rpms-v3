/**
 *	
 *	@author		: MEDJERAB ABIR
 *
 *	@Name		: AuditAspect
 *	@CreatedOn	: 26-06-2025
 *
 *	@Type		: Class
 *	@Layaer		: Configuration
 *	@Goal		: Audit
 *
 **/

package dz.mdn.rpms.audit.config;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import dz.mdn.rpms.audit.service.AuditService;
import lombok.RequiredArgsConstructor;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
    private final AuditService auditService;

    @AfterReturning(
        pointcut = "@annotation(auditable)",
        returning = "result")
    public void auditSuccessfulOperation(JoinPoint joinPoint, Auditable auditable, Object result) {
        Object resourceId = extractResourceId(result, auditable.idField());
        auditService.logEvent(
            auditable.action(),
            auditable.resourceType(),
            resourceId != null ? Long.parseLong(resourceId.toString()) : null,
            joinPoint.getArgs());
    }

    @AfterThrowing(
        pointcut = "@annotation(auditable)",
        throwing = "ex")
    public void auditFailedOperation(JoinPoint joinPoint, Auditable auditable, Exception ex) {
        auditService.logEvent(
            auditable.action() + "_FAILED",
            auditable.resourceType(),
            null,
            joinPoint.getArgs());
    }

    private Object extractResourceId(Object result, String idField) {
        try {
            if (result == null) return null;
            if (idField.isEmpty()) return null;
            
            if (result instanceof ResponseEntity) {
                result = ((ResponseEntity<?>) result).getBody();
            }
            
            if (result == null) return null;
            
            PropertyDescriptor pd = new PropertyDescriptor(idField, result.getClass());
            Method getter = pd.getReadMethod();
            return getter.invoke(result);
        } catch (Exception e) {
            return null;
        }
    }
}