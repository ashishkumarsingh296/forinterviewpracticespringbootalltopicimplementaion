package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderSaga {

    @Id
    private String sagaId;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private SagaStatus status;

    private LocalDateTime createdAt;
}
