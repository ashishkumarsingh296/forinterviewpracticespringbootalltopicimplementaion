package com.example.forinterviewpracticespringbootalltopicimplementaion.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspects {

    @Around("execution(* com.example.forinterviewpracticespringbootalltopicimplementaion.service..*(..))")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {

        long startTime = System.currentTimeMillis();
        String method = joinPoint.getSignature().toShortString();

        log.info("🔄 SERVICE START | method={} | args={}",
                method, Arrays.toString(joinPoint.getArgs()));

        Object response = joinPoint.proceed();   // Real method executes

        long timeTaken = System.currentTimeMillis() - startTime;

        if (response == null) {
            log.info("✅ SERVICE END | method={} | time={}ms | response=void",
                    method, timeTaken);
        } else {
            log.info("✅ SERVICE END | method={} | time={}ms | response={}",
                    method, timeTaken, response);
        }

        return response;
    }
}
