package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class PaymentController {

    private final PaymentService paymentService;

    // ✅ Create Dummy Payment
    @PostMapping("/payments/addPayment")
    public ResponseEntity<PaymentResponseDTO> createPayment(
            @RequestBody CreatePaymentRequestDTO dto) {

        return ResponseEntity.ok(paymentService.createDummyPayment(dto));
    }

    // ✅ Mark Payment Success / Failed
    @PutMapping("/payments/{paymentId}/status")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam String status) {

        paymentService.completeDummyPayment(paymentId, status);
        return ResponseEntity.ok("Payment updated");
    }
}
