package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Payment;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final WalletService walletService;

    public PaymentResponseDTO createDummyPayment(CreatePaymentRequestDTO dto) {

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        // Prevent duplicate payment
        if (paymentRepository.findByOrderId(dto.getOrderId()).isPresent())
            throw new IllegalStateException("Payment already exists for this order");

        // 🔥 1️⃣ Check wallet balance first
        walletService.checkSufficientBalance(order.getUser().getId(), dto.getAmount(),
                "PAYMENT_ORDER_" + dto.getOrderId());

        // 🔥 2️⃣ Deduct wallet money
//        walletService.deductAmount(order.getUser().getId(), dto.getAmount(),
//                "PAYMENT_ORDER_" + dto.getOrderId());

        // 🔥 3️⃣ Create payment entry
        Payment payment = Payment.builder()
                .paymentReference("DUMMY_TXN_" + UUID.randomUUID())
                .amount(dto.getAmount())
                .paymentMethod(dto.getMethod())
                .status("PAID")  // payment successful because wallet deducted
                .order(order)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        // 🔥 4️⃣ Update order status
        order.setPayment(payment);
        order.setStatus("PAID");
        orderRepository.save(order);

        // 🔥 5️⃣ Prepare response
        return PaymentResponseDTO.builder()
                .id(payment.getId())
                .paymentReference(payment.getPaymentReference())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .method(payment.getPaymentMethod())
                .orderId(order.getId())
                .build();
    }



    public void completeDummyPayment(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        payment.setStatus(status.toUpperCase());
        Order order = payment.getOrder();
        order.setStatus(status.equalsIgnoreCase("PAID") ? "PAID" : "FAILED");

        paymentRepository.save(payment);
        orderRepository.save(order);
    }
}
