package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequestDTO {

    @NotNull
    private Long userId;

    @NotNull
    private Double totalAmount;

    @NotNull
    private String status;
}
