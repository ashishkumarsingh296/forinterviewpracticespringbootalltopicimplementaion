package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.Data;

@Data
public class CreatePaymentRequestDTO {
    private Long orderId;
    private Double amount;
    private String method;   // UPI / CARD / CASH
}
