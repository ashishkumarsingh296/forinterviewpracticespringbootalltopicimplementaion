package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public RefundResponseDTO requestRefund(@RequestBody RefundRequestDTO dto) {
        return refundService.requestRefund(dto);
    }
}
