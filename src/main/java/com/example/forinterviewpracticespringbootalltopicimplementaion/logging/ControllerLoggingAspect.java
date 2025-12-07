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
public class ControllerLoggingAspect {

    @Around("execution(* com.example..controller..*(..))")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {

        String method = joinPoint.getSignature().toShortString();

        log.info("➡️ CONTROLLER START | method={} | args={}",
                method, Arrays.toString(joinPoint.getArgs()));

        Object response = joinPoint.proceed();

        log.info("✅ CONTROLLER END | method={} | response={}",
                method, response);

        return response;
    }
}
