package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
    private String role; // "ADMIN" or "USER" (optional)
}
