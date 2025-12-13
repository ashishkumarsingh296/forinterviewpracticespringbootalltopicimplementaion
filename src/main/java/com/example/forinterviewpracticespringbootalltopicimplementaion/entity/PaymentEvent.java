package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import lombok.Data;

@Data
public class PaymentEvent {

    private String orderId;
    private String eventType;

    public PaymentEvent(String orderId, String eventType) {
        this.orderId = orderId;
        this.eventType = eventType;
    }
}
