package com.example.forinterviewpracticespringbootalltopicimplementaion.constants;

public final class SecurityExpressions {
    private SecurityExpressions() {}

    public static final String HAS_ROLE_USER = "hasRole('USER')";
    public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String HAS_ANY_ROLE_USER_ADMIN = "hasAnyRole('USER','ADMIN')";
}
