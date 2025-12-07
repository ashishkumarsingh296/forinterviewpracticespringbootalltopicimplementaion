package com.example.forinterviewpracticespringbootalltopicimplementaion.common;

import com.example.forinterviewpracticespringbootalltopicimplementaion.customanotation.Auditable;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.AuditLogService;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {
//Business (Audit) Logs
    private final AuditLogService auditService;

    @Around("@annotation(auditable)")
    public Object logAudit(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {

        Object result = joinPoint.proceed();

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        String entity = auditable.entity();
        String action = auditable.action();
        Boolean isDeleted = Boolean.valueOf(auditable.action());


        Long targetId = extractTargetEntityId(result);

        auditService.log(username, entity, action, isDeleted,"Action performed successfully", targetId);

        return result;
    }
    private Long extractTargetEntityId(Object result) {
        if (result == null) return null;

        try {
            // Case 1: Entity or DTO has `getId()` method
            var method = result.getClass().getMethod("getId");
            Object idValue = method.invoke(result);
            return idValue != null ? Long.valueOf(idValue.toString()) : null;
        } catch (Exception ignored) {
        }

        // Case 2: ResponseEntity wrapper
        if (result instanceof ResponseEntity<?> response) {
            Object body = response.getBody();
            return extractTargetEntityId(body);
        }

        return null;
    }

}
