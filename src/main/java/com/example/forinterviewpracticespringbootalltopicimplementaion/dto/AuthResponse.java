package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private String message;
    private String tokenType;
    private Set<String> roles; // ✅ added
}
