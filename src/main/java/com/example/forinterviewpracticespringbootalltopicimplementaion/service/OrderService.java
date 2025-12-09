package com.example.forinterviewpracticespringbootalltopicimplementaion.service;


import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreateOrderRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.UpdateOrderStatusDTO;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;

public interface OrderService {

    OrderResponseDTO createOrder(CreateOrderRequestDTO dto);

    @Cacheable(value = "userOrders", key = "#userId")
    Page<OrderResponseDTO> getUserOrders(Long userId, Pageable pageable);
    @Cacheable(value = "userOrders", key = "#orderId")
    OrderResponseDTO getOrderById(Long orderId);
    void updateOrderStatus(Long orderId, UpdateOrderStatusDTO dto);

    void softDeleteOrder(Long orderId);
}
