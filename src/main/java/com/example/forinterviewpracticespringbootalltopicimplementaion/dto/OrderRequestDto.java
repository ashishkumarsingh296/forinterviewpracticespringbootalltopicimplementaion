package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDto {
    private String orderNumber;
    private Long userId;
    private String status;
    private List<OrderItemDTO> items;

    // Payment info
    private String paymentMethod;
}
