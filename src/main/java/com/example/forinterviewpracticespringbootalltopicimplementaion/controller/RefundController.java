package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping("/refunds/requestRefund")
    public RefundResponseDTO requestRefund(@RequestParam Long orderId) {
        return refundService.refund(orderId);
    }
}
