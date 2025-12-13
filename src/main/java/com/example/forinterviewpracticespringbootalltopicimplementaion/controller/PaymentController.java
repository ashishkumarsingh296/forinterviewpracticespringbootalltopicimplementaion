package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("payments/pay")
    public ResponseEntity<PaymentResponseDTO> pay(
            @RequestBody CreatePaymentRequestDTO dto) {

        return ResponseEntity.ok(paymentService.createDummyPayment(dto));
    }
}
