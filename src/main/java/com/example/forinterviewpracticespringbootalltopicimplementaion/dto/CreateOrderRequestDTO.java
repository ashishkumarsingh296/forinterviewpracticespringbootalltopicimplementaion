package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestDTO {
    private Long userId;
    private List<OrderItemDTO> items;
    private String status; // CREATED, PENDING, etc.
    private String paymentMethod; // CREDIT_CARD, PAYPAL, etc.
}
