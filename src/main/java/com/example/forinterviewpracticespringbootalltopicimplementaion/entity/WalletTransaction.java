package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_transactions")
@Data
public class WalletTransaction {

    @Id
    private Long id;

    private Long userId;

    private Double amount;

    @Enumerated(EnumType.STRING)
    private TxType type;

    private Double balance;

    private String reference;

    private LocalDateTime createdAt;
}
