package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.PaymentResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentHistoryService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class PaymentHistoryController {

    private final PaymentHistoryService paymentHistoryService;

    @GetMapping("/payments/user/{userId}")
    public ResponseEntity<Page<PaymentResponseDTO>> getPayments(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PaymentResponseDTO> p = paymentHistoryService.getPaymentsByUser(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(p);
    }
}
