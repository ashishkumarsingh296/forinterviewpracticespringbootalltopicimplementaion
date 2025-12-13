package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor   // 🔥 REQUIRED FOR JACKSON
@AllArgsConstructor
@Data
public class PaymentEvent {

    private String orderId;
    private String eventType;

}
