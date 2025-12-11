package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemResponseDTO {
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
