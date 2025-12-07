package com.example.forinterviewpracticespringbootalltopicimplementaion.logging;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ExceptionLoggingAspect {

    @AfterThrowing(pointcut = "within(@org.springframework.stereotype.Service *)", throwing = "ex")
    public void logException(Exception ex) {
        log.error("❌ EXCEPTION OCCURRED | message={}", ex.getMessage(), ex);
    }
}
