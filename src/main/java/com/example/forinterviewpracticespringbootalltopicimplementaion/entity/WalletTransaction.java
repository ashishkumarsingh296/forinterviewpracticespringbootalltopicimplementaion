package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Data
public class WalletTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private Double amount;

    @Enumerated(EnumType.STRING)
    private TxType type;

    private Double balance;
    private String reference;

    @CreationTimestamp
    private LocalDateTime createdAt;
}



