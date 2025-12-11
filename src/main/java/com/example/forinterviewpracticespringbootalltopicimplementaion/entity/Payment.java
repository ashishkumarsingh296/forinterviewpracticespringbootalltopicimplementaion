package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String paymentReference;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String status;  // PENDING, PAID, FAILED

    private String paymentMethod;  // UPI, CARD, CASH

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private LocalDateTime createdAt;
}
