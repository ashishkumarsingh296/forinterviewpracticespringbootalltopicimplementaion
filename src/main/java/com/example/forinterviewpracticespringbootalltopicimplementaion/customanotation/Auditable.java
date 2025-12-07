package com.example.forinterviewpracticespringbootalltopicimplementaion.customanotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {
    String action();
    String entity();
    String isDeleted();
}
