package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreateOrderRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.UpdateOrderStatusDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.User;
import com.example.forinterviewpracticespringbootalltopicimplementaion.mapper.OrderMapper;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import io.micrometer.core.instrument.MeterRegistry;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderMapper mapper;
    private final AuditLogService auditLogService;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final MeterRegistry meterRegistry;

    /** CREATE ORDER */
    @Override
    @CacheEvict(value = "userOrders", key = "#dto.userId")
    public OrderResponseDTO createOrder(CreateOrderRequestDTO dto) {
        meterRegistry.counter("orders.created").increment(); // Micrometer metric

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .totalAmount(dto.getTotalAmount())
                .status(dto.getStatus())
                .user(user)
                .isDeleted(false)
                .build();

        Order saved = orderRepository.save(order);

        // Audit log
        auditLogService.log(currentUsername(), "Order", "CREATED", false, "Order created", saved.getId());

        // Kafka event
        kafkaTemplate.send("orders-topic", "Order created: " + saved.getId());

        return mapper.toDTO(saved);
    }

    /** GET USER ORDERS WITH CACHE */
    @Override
    @Cacheable(value = "userOrders", key = "#userId")
    public Page<OrderResponseDTO> getUserOrders(Long userId, Pageable pageable) {
        return orderRepository.findByUserIdAndIsDeletedFalse(userId, pageable)
                .map(mapper::toDTO);
    }

    /** UPDATE ORDER STATUS */
    @Override
    @CacheEvict(value = "userOrders", key = "#orderId")
    public void updateOrderStatus(Long orderId, UpdateOrderStatusDTO dto) {
        meterRegistry.counter("orders.updated").increment();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setStatus(dto.getStatus());
        Order saved = orderRepository.save(order);

        auditLogService.log(currentUsername(), "Order", "UPDATED", false, "Order status updated", saved.getId());
        kafkaTemplate.send("orders-topic", "Order updated: " + saved.getId());
    }

    /** SOFT DELETE ORDER */
    @Override
    @CacheEvict(value = "userOrders", key = "#orderId")
    public void softDeleteOrder(Long orderId) {
        meterRegistry.counter("orders.deleted").increment();

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setDeleted(true); // ✅ Soft delete
        Order saved = orderRepository.save(order);

        auditLogService.log(currentUsername(), "Order", "DELETED", true, "Order soft-deleted", saved.getId());
        kafkaTemplate.send("orders-topic", "Order deleted: " + saved.getId());
    }

    /** MOCK CURRENT USERNAME (replace with actual auth logic) */
    private String currentUsername() {
        return "system_user"; // or fetch from Spring Security context
    }
}
