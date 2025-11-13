package com.example.forinterviewpracticespringbootalltopicimplementaion.logging;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class MdcLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        try {
            // Generate a unique trace ID per request
            String traceId = UUID.randomUUID().toString().substring(0, 8);
            MDC.put("traceId", traceId);

            // Capture username & roles from Spring Security
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated()) {
                MDC.put("username", auth.getName());
                MDC.put("role", auth.getAuthorities().toString());
            } else {
                MDC.put("username", "anonymous");
                MDC.put("role", "N/A");
            }

            chain.doFilter(request, response);

        } finally {
            // Always clear MDC to avoid leaking info across threads
            MDC.clear();
        }
    }
}
