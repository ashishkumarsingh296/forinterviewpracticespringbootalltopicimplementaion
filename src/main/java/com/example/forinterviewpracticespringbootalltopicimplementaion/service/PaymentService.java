package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.*;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.IdempotentRequestRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.PaymentRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final IdempotentRequestRepository idempotentRepo;
    private final OrderRepository orderRepo;
    private final WalletService walletService;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final PaymentRepository paymentRepository;
    @Autowired
    private RedisLockService lockService;


    @Retry(
            name = "paymentRetry",
            fallbackMethod = "paymentFallback"
    )
    public PaymentResponseDTO pay(String idempotencyKey, Long orderId) throws Exception {

        String lockKey = "PAYMENT_LOCK_" + orderId;
        PaymentResponseDTO response;

        if (!lockService.acquireLock(lockKey, 30)) {
            throw new IllegalStateException("Payment already in progress");
        }
        try {
            // 1️⃣ Idempotency check
            Optional<IdempotentRequest> existing = idempotentRepo.findById(idempotencyKey);
            if (existing.isPresent()) {
                return objectMapper.readValue(existing.get().getResponse(), PaymentResponseDTO.class);
            }

            // 2️⃣ Fetch order
            Order order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException("Order not found"));

            if (order.getOrderStatus() == OrderStatus.PAID) {
                throw new IllegalStateException("Order already paid");
            }

            // 3️⃣ Create Payment record
            Payment payment = new Payment();
            payment.setOrder(order);
            payment.setAmount(order.getTotalAmount());
            payment.setPaymentMethod("WALLET");
            payment.setPaymentReference("WALLET-" + order.getId() + "-" + System.currentTimeMillis());
            payment.setStatus("SUCCESS");
            paymentRepository.save(payment);

            // 5️⃣ Debit wallet
            walletService.debit(String.valueOf(order.getUser().getId()), order.getTotalAmount(),payment.getPaymentReference());

            // 6️⃣ Mark order as PAID
            order.setOrderStatus(OrderStatus.PAID);
            orderRepo.save(order);

            // 7️⃣ Build response
             response = PaymentResponseDTO.builder()
                    .status("SUCCESS")
                    .orderId(orderId)
                    .amount(order.getTotalAmount())
                    .method("WALLET")
                    .build();

            // 6️⃣ Save idempotent request
            IdempotentRequest record = new IdempotentRequest();
            record.setIdempotencyKey(idempotencyKey);
            record.setResponse(objectMapper.writeValueAsString(response));
            idempotentRepo.save(record);

            // 7️⃣ Publish event
            kafkaTemplate.send(
                    "payment-events",
                    new PaymentEvent(orderId.toString(), "PAYMENT_SUCCESS")
            );
        } finally {
            lockService.releaseLock(lockKey);

        }
        return response;

    }

    // ✅ FALLBACK MUST MATCH SIGNATURE + Exception
    public PaymentResponseDTO paymentFallback(
            String idempotencyKey,
            Long orderId,
            Exception ex) {

        return PaymentResponseDTO.builder()
                .status("FAILED_AFTER_RETRY")
                .orderId(orderId)
                .build();
    }
}
