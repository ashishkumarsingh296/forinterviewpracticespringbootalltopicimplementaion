package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Double amount;
    private String status;        // PAID / FAILED / PENDING
    private String paymentMethod; // CARD / UPI etc.
}
