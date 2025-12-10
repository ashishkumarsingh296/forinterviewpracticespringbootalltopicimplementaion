package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Payment;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    public PaymentResponseDTO createDummyPayment(CreatePaymentRequestDTO dto) {

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // ❌ Prevent duplicate payments
        if (paymentRepository.findByOrderId(dto.getOrderId()).isPresent()) {
            throw new IllegalStateException("Payment already exists for this order");
        }

        Payment payment = Payment.builder()
                .paymentReference("DUMMY_TXN_" + UUID.randomUUID())
                .amount(dto.getAmount())
                .method(dto.getMethod())
                .status("PENDING")
                .order(order)
                .createdAt(LocalDateTime.now())
                .build();

        order.setPayment(payment);
        order.setStatus("PENDING");

        paymentRepository.save(payment);

        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .method(payment.getMethod())
                .orderId(order.getId())
                .build();
    }

    // ✅ Simulate Payment Success / Failure
    public void completeDummyPayment(Long paymentId, String status) {

        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        payment.setStatus(status.toUpperCase());

        Order order = payment.getOrder();

        if ("PAID".equalsIgnoreCase(status)) {
            order.setStatus("PAID");
        } else {
            order.setStatus("FAILED");
        }

        paymentRepository.save(payment);
        orderRepository.save(order);
    }
}
