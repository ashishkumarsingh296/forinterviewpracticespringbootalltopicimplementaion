package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import com.example.forinterviewpracticespringbootalltopicimplementaion.common.BaseResponse;
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
    private Long orderId;
    private Double amount;
    private String status;
    private String reason;
    private LocalDateTime createdAt;
}
