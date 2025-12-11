package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundResponseDTO {
    private Long id;
    private Long paymentId;
    private Double amount;
    private String status;
    private String reason;
    private LocalDateTime createdAt;
}
