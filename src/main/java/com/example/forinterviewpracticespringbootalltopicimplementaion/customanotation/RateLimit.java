package com.example.forinterviewpracticespringbootalltopicimplementaion.customanotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimit {
    int maxRequests() default 5;     // Max requests
    int timeWindow() default 60;     // Time window in seconds
}
