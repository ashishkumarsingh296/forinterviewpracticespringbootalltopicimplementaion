package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefundRequestDTO {
    private Long paymentId;
    private Double amount;
    private String reason;
}
