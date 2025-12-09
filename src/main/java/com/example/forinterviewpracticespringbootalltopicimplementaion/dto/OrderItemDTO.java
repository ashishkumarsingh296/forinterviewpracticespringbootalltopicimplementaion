package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long productId;
    private String productName; // optional for response
    private Integer quantity;
    private Double price;       // total price for this item (product.price * quantity)
}
