package com.example.forinterviewpracticespringbootalltopicimplementaion.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateOrderStatusDTO {

    @NotBlank
    private String status;
}
