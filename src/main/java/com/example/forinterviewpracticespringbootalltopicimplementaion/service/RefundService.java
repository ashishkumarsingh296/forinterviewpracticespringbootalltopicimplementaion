package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Payment;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Refund;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.PaymentRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.RefundRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.RefundRequestDTO;
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

        if (dto.getAmount() <= 0 || dto.getAmount() > payment.getAmount()) {
            throw new IllegalArgumentException("Invalid refund amount");
        }

        // create refund record as REQUESTED
        Refund refund = Refund.builder()
                .payment(payment)
                .amount(dto.getAmount())
                .reason(dto.getReason())
                .status("REQUESTED")
                .build();

        refund = refundRepository.save(refund);

        // For dummy implementation, immediately mark as REFUNDED and update payment/refunded amount
        refund.setStatus("REFUNDED");
        refundRepository.save(refund);

        // update payment - add refunded amount field if needed (we will set refundedAmount dynamically)
        payment.setStatus("REFUNDED"); // optional: reflect refund on payment
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
