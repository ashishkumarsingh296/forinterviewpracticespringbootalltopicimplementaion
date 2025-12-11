package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public PaymentResponseDTO createPayment(@RequestBody CreatePaymentRequestDTO dto) {
        return paymentService.createDummyPayment(dto);
    }

    @PostMapping("/{paymentId}/complete")
    public void completePayment(@PathVariable Long paymentId, @RequestParam String status) {
        paymentService.completeDummyPayment(paymentId, status);
    }
}
