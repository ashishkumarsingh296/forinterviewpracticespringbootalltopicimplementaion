package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;


import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Refund;
import org.springframework.stereotype.Component;

@Component
public class RefundMapper {

    public RefundDTO toDTO(Refund refund) {
        if (refund == null) return null;
        RefundDTO dto = new RefundDTO();
        dto.setRefundId(refund.getId());
        dto.setPaymentId(refund.getPayment() != null ? refund.getPayment().getId() : null);
        dto.setStatus(refund.getStatus());
        dto.setRefundedAt(refund.getRefundedAt());
        dto.setReason(refund.getReason());
        return dto;
    }
}
