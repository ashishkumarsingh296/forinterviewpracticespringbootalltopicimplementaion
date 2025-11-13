package com.example.forinterviewpracticespringbootalltopicimplementaion.common;

import com.example.forinterviewpracticespringbootalltopicimplementaion.customanotation.RateLimit;
//import com.example.forinterviewpracticespringbootalltopicimplementaion.service.RateLimiterService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.RateLimiterService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RateLimitAspect {

    private final RateLimiterService rateLimiterService;
    private final HttpServletRequest request;

    public RateLimitAspect(RateLimiterService rateLimiterService, HttpServletRequest request) {
        this.rateLimiterService = rateLimiterService;
        this.request = request;
    }

    @Around("@annotation(rateLimit)")
    public Object checkRateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        // 1️⃣ Get userId from JWT
        String userId = getCurrentUserId();
        String key;

        if (userId != null) {
            key = "user:" + userId; // rate limit per user
        } else {
            key = "ip:" + request.getRemoteAddr(); // fallback to IP for anonymous
        }

        boolean allowed = rateLimiterService.isAllowed(
                key,
                rateLimit.maxRequests(),
                rateLimit.timeWindow()
        );

        if (!allowed) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                    .body("Too many requests. Please try again later.");
        }

        return joinPoint.proceed();
    }

    private String getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getPrincipal())) {
            return auth.getName(); // or extract userId from JWT claims
        }
        return null;
    }
}
