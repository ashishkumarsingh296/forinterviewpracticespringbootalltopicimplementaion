package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.*;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderItem;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Payment;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.User;
import com.example.forinterviewpracticespringbootalltopicimplementaion.mapper.OrderMapper;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.ProductRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j

public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderMapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @CacheEvict(value = "userOrders", key = "#dto.userId")
    public OrderResponseDTO createOrder(CreateOrderRequestDTO dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .status(dto.getStatus())
                .user(user)
                .isDeleted(false)
                .build();

        // Order Items
        List<OrderItem> items = new ArrayList<>();
        double totalAmount = 0.0;
        for (OrderItemDTO itemDTO : dto.getItems()) {
            var product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Product not found"));
            double price = product.getPrice() * itemDTO.getQuantity();
            totalAmount += price;

            OrderItem item = OrderItem.builder()
                    .order(order)
                    .product(product)
                    .quantity(itemDTO.getQuantity())
                    .price(price)
                    .build();
            items.add(item);
        }
        order.setItems(items);
        order.setTotalAmount(totalAmount);

        // No payment processed yet
        order.setPayment(null);  // optional, can omit if already null

        Order saved = orderRepository.save(order);

        // Kafka event
        kafkaTemplate.send("orders-topic", "Order created: " + saved.getId());
        log.info("Order {} created", saved.getId());

        // Map to DTO
        OrderResponseDTO dtoResponse = mapper.toDTO(saved);
        dtoResponse.setPaymentStatus("No payment processed yet"); // ✅ indicate payment status
        return dtoResponse;
    }

    @Cacheable(value = "userOrders", key = "#userId")
    public Page<OrderResponseDTO> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserIdAndIsDeletedFalse(userId, pageable)
                .map(mapper::toDTO);
    }

    @Cacheable(value = "order", key = "#orderId")
    public OrderResponseDTO getOrderById(Long orderId) {
        return mapper.toDTO(orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found")));
    }

    @CacheEvict(value = {"order", "userOrders"}, allEntries = true)
    public void updateOrderStatus(Long orderId, UpdateOrderStatusDTO dto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        String status = dto.getStatus().toUpperCase();
        order.setStatus(status);

        // Sync payment
        if (order.getPayment() != null) {
            switch (status) {
                case "PAID", "DELIVERED" -> order.getPayment().setStatus("PAID");
                case "CANCELLED", "FAILED" -> order.getPayment().setStatus("FAILED");
                case "PENDING" -> order.getPayment().setStatus("PENDING");
            }
        }

        orderRepository.save(order);
        kafkaTemplate.send("orders-topic", "Order status updated: " + orderId + " -> " + status);
    }

    @CacheEvict(value = "userOrders", key = "#orderId")
    public void softDeleteOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        order.setDeleted(true);
        orderRepository.save(order);
        kafkaTemplate.send("orders-topic", "Order deleted: " + orderId);
    }
}
