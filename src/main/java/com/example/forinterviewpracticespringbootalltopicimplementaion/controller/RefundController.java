package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.RefundResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.RefundRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.RefundService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")

public class RefundController {

    private final RefundService refundService;

    @PostMapping("refunds/craeteRefund")
    public ResponseEntity<RefundResponseDTO> createRefund(@RequestBody RefundRequestDTO dto) {
        RefundResponseDTO resp = refundService.requestRefund(dto);
        return ResponseEntity.ok(resp);
    }
}
