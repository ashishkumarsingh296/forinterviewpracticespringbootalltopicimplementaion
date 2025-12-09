package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusDTO {
    private String status; // PAID, CANCELLED, FAILED, PENDING, etc.
}
