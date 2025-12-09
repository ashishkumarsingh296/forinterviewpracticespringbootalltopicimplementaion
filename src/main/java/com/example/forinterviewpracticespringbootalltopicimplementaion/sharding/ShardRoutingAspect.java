//package com.example.forinterviewpracticespringbootalltopicimplementaion.sharding;
//
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.*;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.stereotype.Component;
//import java.lang.annotation.Annotation;
//import java.lang.reflect.Method;
//
//@Aspect
//@Component
//public class ShardRoutingAspect {
//
//    private final int shardCount = 3;
//
//    @Around("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.GetMapping)")
//    public Object routeShard(ProceedingJoinPoint pjp) throws Throwable {
//        MethodSignature sig = (MethodSignature) pjp.getSignature();
//        Method method = sig.getMethod();
//        Object[] args = pjp.getArgs();
//        Annotation[][] paramAnns = method.getParameterAnnotations();
//
//        Long userId = null;
//        for (int i = 0; i < paramAnns.length; i++) {
//            for (Annotation a : paramAnns[i]) {
//                if (a.annotationType().equals(ShardKey.class)) {
//                    Object arg = args[i];
//                    if (arg instanceof Long) userId = (Long) arg;
//                    else if (arg instanceof String) userId = Long.valueOf((String) arg);
//                }
//            }
//        }
//
//        if (userId != null) {
//            String shard = "shard" + (Math.abs(userId % shardCount));
//            try {
//                ShardContextHolder.setShard(shard);
//                return pjp.proceed();
//            } finally {
//                ShardContextHolder.clear();
//            }
//        } else {
//            return pjp.proceed();
//        }
//    }
//}
