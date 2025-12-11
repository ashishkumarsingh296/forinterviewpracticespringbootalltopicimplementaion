package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;


import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentDTO toDTO(Payment payment) {
        if (payment == null) return null;
        PaymentDTO dto = new PaymentDTO();
        dto.setPaymentId(payment.getId());
        dto.setOrderId(payment.getOrder() != null ? payment.getOrder().getId() : null);
        dto.setAmount(payment.getAmount());
        dto.setStatus(payment.getStatus());
        dto.setMethod(payment.getPaymentMethod());
        dto.setCreatedAt(payment.getCreatedAt());
        return dto;
    }
}
