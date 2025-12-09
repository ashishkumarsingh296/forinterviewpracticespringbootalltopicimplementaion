package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreateOrderRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderResponseDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.UpdateOrderStatusDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.OrderService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /** CREATE ORDER */
    @PostMapping("/orders/addOrder")
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody CreateOrderRequestDTO dto) {
        OrderResponseDTO order = orderService.createOrder(dto);
        return ResponseEntity.ok(order);
    }

    /** GET SINGLE ORDER BY ID */
    @GetMapping("/orders/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrder(@PathVariable Long orderId) {
        OrderResponseDTO dto = orderService.getOrderById(orderId);
        return ResponseEntity.ok(dto);
    }

    /** GET USER ORDERS (paginated) */
    @GetMapping("/orders/user/{userId}")
    public ResponseEntity<Page<OrderResponseDTO>> getUserOrders(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        Page<OrderResponseDTO> orders = orderService.getUserOrders(userId, pageable);
        return ResponseEntity.ok(orders);
    }

    /** UPDATE ORDER STATUS */
    @PatchMapping("/orders/{orderId}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestBody UpdateOrderStatusDTO dto
    ) {
        orderService.updateOrderStatus(orderId, dto);
        return ResponseEntity.ok().build();
    }

    /** SOFT DELETE ORDER */
    @DeleteMapping("/orders/delete/{orderId}")
    public ResponseEntity<Void> softDeleteOrder(@PathVariable Long orderId) {
        orderService.softDeleteOrder(orderId);
        return ResponseEntity.ok().build();
    }
}
