package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundRequestDTO {
    private Long paymentId;
    private Double amount;
    private String reason;
}
