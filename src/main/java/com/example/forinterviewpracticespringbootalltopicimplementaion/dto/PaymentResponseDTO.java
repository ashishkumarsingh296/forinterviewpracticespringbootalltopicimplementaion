package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentResponseDTO {
    private Long id;
    private String paymentReference;
    private Double amount;
    private String status;
    private String method;
    private Long orderId;
}
