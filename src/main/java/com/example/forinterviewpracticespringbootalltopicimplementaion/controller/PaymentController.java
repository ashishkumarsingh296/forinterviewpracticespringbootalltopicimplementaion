package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // ✅ Create Dummy Payment
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @RequestBody CreatePaymentRequestDTO dto) {

        return ResponseEntity.ok(paymentService.createDummyPayment(dto));
    }

    // ✅ Mark Payment Success / Failed
    @PutMapping("/{paymentId}/status")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam String status) {

        paymentService.completeDummyPayment(paymentId, status);
        return ResponseEntity.ok("Payment updated");
    }
}
