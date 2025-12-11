package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class OrderDTO {
    private Long id;
    private String orderNumber;
    private String status;
    private Long userId;              // ✅ add this
    private String paymentStatus;     // ✅ add this
    private String paymentMethod;     // ✅ add this
    private Double totalAmount;
    private LocalDateTime createdAt;
    private List<OrderItemDTO> items;
}
