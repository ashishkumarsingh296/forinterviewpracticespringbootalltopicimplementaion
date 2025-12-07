package com.example.forinterviewpracticespringbootalltopicimplementaion.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class ApiLoggingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long startTime = System.currentTimeMillis();

        String method = request.getMethod();
        String uri = request.getRequestURI();

        log.info("➡️ API START | method={} | uri={}", method, uri);

        filterChain.doFilter(request, response);

        long timeTaken = System.currentTimeMillis() - startTime;

        log.info("✅ API END | method={} | uri={} | status={} | time={}ms",
                method, uri, response.getStatus(), timeTaken);
    }
}
