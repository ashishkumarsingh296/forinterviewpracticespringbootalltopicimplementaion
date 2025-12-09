package com.example.forinterviewpracticespringbootalltopicimplementaion.service;


import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreateOrderRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.UpdateOrderStatusDTO;
import org.springframework.data.domain.*;

public interface OrderService {

    OrderResponseDTO createOrder(CreateOrderRequestDTO dto);

    Page<OrderResponseDTO> getUserOrders(Long userId, Pageable pageable);

    void updateOrderStatus(Long orderId, UpdateOrderStatusDTO dto);

    void softDeleteOrder(Long orderId);
}
