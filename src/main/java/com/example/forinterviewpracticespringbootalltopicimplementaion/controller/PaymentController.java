package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/payments/createPayment")
    public PaymentResponseDTO createPayment(@RequestBody CreatePaymentRequestDTO dto) {
        return paymentService.createDummyPayment(dto);
    }

    @PostMapping("/payments/complete/{paymentId}")
    public void completePayment(@PathVariable Long paymentId, @RequestParam String status) {
        paymentService.completeDummyPayment(paymentId, status);
    }
}
