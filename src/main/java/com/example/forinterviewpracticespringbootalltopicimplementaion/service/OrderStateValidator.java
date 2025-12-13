package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderStatus;
import org.springframework.stereotype.Component;

@Component
public class OrderStateValidator {

    public void validate(OrderStatus current, OrderStatus next) {

        if (current == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Delivered order cannot change");
        }

        if (current == OrderStatus.CANCELLED && next != OrderStatus.REFUNDED) {
            throw new IllegalStateException("Invalid cancel transition");
        }
    }
}
