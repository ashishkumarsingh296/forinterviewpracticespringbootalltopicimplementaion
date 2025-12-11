package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Payment;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Refund;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.PaymentRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.RefundRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;

    @Transactional
    public RefundResponseDTO requestRefund(RefundRequestDTO dto) {
        Payment payment = paymentRepository.findById(dto.getPaymentId())
                .orElseThrow(() -> new EntityNotFoundException("Payment not found"));

        if (dto.getAmount() <= 0 || dto.getAmount() > payment.getAmount())
            throw new IllegalArgumentException("Invalid refund amount");

        Refund refund = Refund.builder()
                .payment(payment)
                .amount(dto.getAmount())
                .reason(dto.getReason())
                .status("REFUNDED")
                .build();

        refund = refundRepository.save(refund);

        payment.setStatus("REFUNDED");
        paymentRepository.save(payment);

        return RefundResponseDTO.builder()
                .id(refund.getId())
                .paymentId(payment.getId())
                .amount(refund.getAmount())
                .status(refund.getStatus())
                .reason(refund.getReason())
                .createdAt(refund.getCreatedAt())
                .build();
    }
}
