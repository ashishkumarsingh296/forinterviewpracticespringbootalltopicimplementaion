package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;


import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderResponseDTO toDTO(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .userId(order.getUser().getId())
                .build();
    }
}
