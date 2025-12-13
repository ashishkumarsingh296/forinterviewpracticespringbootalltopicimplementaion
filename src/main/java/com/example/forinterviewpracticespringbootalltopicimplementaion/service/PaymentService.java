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

            Payment payment = Payment.builder()
                    .paymentReference("TXN_" + UUID.randomUUID())
                    .amount(dto.getAmount())
                    .paymentMethod(dto.getMethod())
                    .status("PENDING")
                    .createdAt(LocalDateTime.now())
                    .order(order)
                    .build();

            walletService.debit(order.getUser().getId(), dto.getAmount(), payment.getPaymentReference());

            payment.setStatus("PAID");
            order.setStatus("PAID");

            paymentRepository.save(payment);

            return PaymentResponseDTO.builder()
                    .id(payment.getId())
                    .paymentReference(payment.getPaymentReference())
                    .amount(payment.getAmount())
                    .status(payment.getStatus())
                    .method(payment.getPaymentMethod())
                    .orderId(order.getId())
                    .build();
        }
    }





