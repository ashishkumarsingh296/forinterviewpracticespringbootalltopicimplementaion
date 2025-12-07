package com.example.forinterviewpracticespringbootalltopicimplementaion.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

    @Aspect
    @Component
    @Slf4j
    @RequiredArgsConstructor    
    public class ServiceLoggingAspects {

        private final ObjectMapper objectMapper = new ObjectMapper();


        // @Around("execution(* com.example.forinterviewpracticespringbootalltopicimplementaion.service..*(..))")
        @AfterReturning(
    pointcut = "execution(* com.example.forinterviewpracticespringbootalltopicimplementaion..*(..))",
    returning = "response"
)
        public void logSuccess(JoinPoint joinPoint, Object response) {

            String method = joinPoint.getSignature().toShortString();
            String user = getCurrentUser();

            try {
                String responseJson = objectMapper.writeValueAsString(response);

                log.info("✅ SUCCESS | User={} | Method={} | Response={}",
                        user, method, responseJson);

            } catch (Exception e) {
                log.info("✅ SUCCESS | User={} | Method={} | Response=NON_SERIALIZABLE",
                        user, method);
            }
        }

        // ✅ Trace FAILURE with Exception
        @AfterThrowing(
    pointcut = "execution(* com.example.forinterviewpracticespringbootalltopicimplementaion..*(..))",
    throwing = "ex"
)
        public void logFailure(JoinPoint joinPoint, Exception ex) {

            String method = joinPoint.getSignature().toShortString();
            String user = getCurrentUser();

            log.error("❌ FAILURE | User={} | Method={} | Exception={}",
                    user, method, ex.getMessage(), ex);
        }

        // ✅ Extract logged-in user from Spring Security
        private String getCurrentUser() {
            try {
                Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                return auth != null ? auth.getName() : "ANONYMOUS";
            } catch (Exception e) {
                return "UNKNOWN";
            }
        }
    }



//        public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
//
//            long startTime = System.currentTimeMillis();
//            String method = joinPoint.getSignature().toShortString();
//
//            log.info("SERVICE START | method={} | args={}",
//                    method, Arrays.toString(joinPoint.getArgs()));
//
//            Object response = joinPoint.proceed();   // ✅ Real method executes here
//
//            long timeTaken = System.currentTimeMillis() - startTime;
//
//            log.info("SERVICE SUCCESS | method={} | time={}ms | response={}",
//                    method, timeTaken, response);
//
//            return response;
//        }
// }


