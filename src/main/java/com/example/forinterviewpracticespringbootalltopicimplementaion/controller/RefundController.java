package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.RefundRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<RefundResponseDTO> createRefund(@RequestBody RefundRequestDTO dto) {
        RefundResponseDTO resp = refundService.requestRefund(dto);
        return ResponseEntity.ok(resp);
    }
}
