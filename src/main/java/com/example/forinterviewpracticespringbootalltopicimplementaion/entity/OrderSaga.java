package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class OrderSaga {

    @Id
    private String sagaId;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private SagaStatus status;

    private LocalDateTime createdAt;
}
