package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "idempotent_requests")
@Data
public class IdempotentRequest {

    @Id
    private String idempotencyKey;

    private String response;

    private LocalDateTime createdAt = LocalDateTime.now();
}
