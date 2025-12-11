package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderItemDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderItem;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderDTO toDTO(Order order) {
        if (order == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setStatus(order.getStatus());
        dto.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());

        // Payment fields
        if (order.getPayment() != null) {
            dto.setPaymentStatus(order.getPayment().getStatus());
            dto.setPaymentMethod(order.getPayment().getPaymentMethod());
        } else {
            dto.setPaymentStatus("No payment processed yet");
            dto.setPaymentMethod(null);
        }

        // Order Items
        if (order.getItems() != null) {
            List<OrderItemDTO> itemDTOs = order.getItems().stream()
                    .map(this::convertItem)
                    .collect(Collectors.toList());
            dto.setItems(itemDTOs);
        }

        return dto;
    }

    private OrderItemDTO convertItem(OrderItem item) {
        return OrderItemDTO.builder()
                .productId(item.getProduct().getId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build();
    }
}
