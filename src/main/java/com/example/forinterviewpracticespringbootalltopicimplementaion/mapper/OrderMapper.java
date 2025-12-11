package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        if (order == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setPaymentStatus(order.getPayment() != null ? order.getPayment().getStatus() : null);
        dto.setPaymentMethod(order.getPayment() != null ? order.getPayment().getPaymentMethod() : null);
        return dto;
    }

}
