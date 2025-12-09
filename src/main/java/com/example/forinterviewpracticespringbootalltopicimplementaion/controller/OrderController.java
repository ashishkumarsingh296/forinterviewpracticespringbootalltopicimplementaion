package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreateOrderRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.UpdateOrderStatusDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_PATH)
@SecurityRequirement(name = "bearerAuth")
public class OrderController {

    private final OrderService service;

    @PostMapping("orders/addOrder")
    public ResponseEntity<OrderResponseDTO> createOrder(
            @RequestBody @Valid CreateOrderRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createOrder(dto));
    }

    @GetMapping("orders/user/{userId}")
    public ResponseEntity<Page<OrderResponseDTO>> getUserOrders(
            @PathVariable Long userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        return ResponseEntity.ok(service.getUserOrders(userId, pageable));
    }

    @PutMapping("orders/{orderId}/status")
    public ResponseEntity<Void> updateStatus(
            @PathVariable Long orderId,
            @RequestBody @Valid UpdateOrderStatusDTO dto) {

        service.updateOrderStatus(orderId, dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("orders/delete/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        service.softDeleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
