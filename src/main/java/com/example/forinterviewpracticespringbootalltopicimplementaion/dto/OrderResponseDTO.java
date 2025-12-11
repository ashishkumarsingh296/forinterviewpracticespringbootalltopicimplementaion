package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderResponseDTO {
    private Long id;
    private String orderNumber;
    private String status;
    private Long userId;
    private double totalAmount;
    private String paymentStatus;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
}
