package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Payment;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentHistoryService {

    private final PaymentRepository paymentRepository;

    public Page<PaymentResponseDTO> getPaymentsByUser(Long userId, Pageable pageable) {
        return paymentRepository.findByOrderUserId(userId, pageable)
                .map(this::toDto);
    }

    private PaymentResponseDTO toDto(Payment p) {
        return PaymentResponseDTO.builder()
                .id(p.getId())
                .paymentReference(p.getPaymentReference())
                .amount(p.getAmount())
                .status(p.getStatus())
                .method(p.getMethod())
                .orderId(p.getOrder() != null ? p.getOrder().getId() : null)
                .build();
    }
}
