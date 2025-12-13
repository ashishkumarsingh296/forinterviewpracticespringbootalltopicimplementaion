package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.IdempotentRequest;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderStatus;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.PaymentEvent;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.IdempotentRequestRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final IdempotentRequestRepository idempotentRepo;
    private final OrderRepository orderRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public PaymentResponseDTO pay(String idempotencyKey, Long orderId) throws Exception {

        // 1️⃣ Idempotency check
        Optional<IdempotentRequest> existing =
                idempotentRepo.findById(idempotencyKey);

        if (existing.isPresent()) {
            return objectMapper.readValue(
                    existing.get().getResponse(),
                    PaymentResponseDTO.class
            );
        }

        // 2️⃣ Order fetch
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getOrderStatus() == OrderStatus.PAID) {
            throw new IllegalStateException("Order already paid");
        }

        // 3️⃣ Mark PAID
        order.setOrderStatus(OrderStatus.valueOf(String.valueOf(OrderStatus.PAID)));
        orderRepo.save(order);

        PaymentResponseDTO response = PaymentResponseDTO.builder()
                .status("SUCCESS")
                .orderId(orderId)
                .build();

        // 4️⃣ Save idempotent record
        IdempotentRequest record = new IdempotentRequest();
        record.setIdempotencyKey(idempotencyKey);
        record.setResponse(objectMapper.writeValueAsString(response));
        idempotentRepo.save(record);

        // 5️⃣ Kafka event
        kafkaTemplate.send(
                "payment-events",
                new PaymentEvent(orderId.toString(), "PAYMENT_SUCCESS")
        );

        return response;
    }
}
