package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderStatus;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RefundService {

    private final OrderRepository orderRepo;
    private final WalletService walletService;

    @Transactional
    public RefundResponseDTO refund(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (order.getOrderStatus() != OrderStatus.PAID) {
            throw new IllegalStateException("Refund not allowed");
        }

        walletService.credit(
                order.getUser().getId().toString(),
                order.getTotalAmount()
        );

        order.setOrderStatus(OrderStatus.REFUNDED);
        orderRepo.save(order);

        return RefundResponseDTO.builder()
                .orderId(order.getId())
                .status("SUCCESS")
                .paymentId(order.getPayment().getId())
                .build();
    }
}



