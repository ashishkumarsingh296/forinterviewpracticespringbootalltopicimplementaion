package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponseDTO {

    private Long id;
    private String orderNumber;
    private Double totalAmount;
    private String status;
    private Long userId;
}
