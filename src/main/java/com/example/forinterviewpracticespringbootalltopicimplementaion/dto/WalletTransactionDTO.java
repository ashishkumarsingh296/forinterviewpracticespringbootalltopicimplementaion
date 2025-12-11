package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WalletTransactionDTO {
    private Long transactionId;
    private Double amount;
    private String type;        // CREDIT / DEBIT / REFUND
    private String reference;
    private LocalDateTime createdAt;
}
