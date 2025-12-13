package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userId;
    private Double balance;
    // Custom constructor for creating a wallet without specifying ID
    public Wallet(String userId, Double balance) {
        this.userId = userId;
        this.balance = balance;
    }

}

