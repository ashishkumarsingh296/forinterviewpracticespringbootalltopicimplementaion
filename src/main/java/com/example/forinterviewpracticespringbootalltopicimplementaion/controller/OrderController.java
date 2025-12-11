package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.*;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/orders/addOrder")
    public OrderDTO createOrder(@RequestBody CreateOrderRequestDTO dto) {
        return orderService.createOrder(dto);
    }

    @GetMapping("/orders/user/{userId}")
    public Page<OrderDTO> getUserOrders(@PathVariable Long userId,
                                        @RequestParam(defaultValue = "0") int page,
                                        @RequestParam(defaultValue = "10") int size) {
        return orderService.getUserOrders(userId, PageRequest.of(page, size));
    }

    @GetMapping("/orders/{orderId}")
    public OrderDTO getOrderById(@PathVariable Long orderId) {
        return orderService.getOrderById(orderId);
    }

    @PutMapping("/orders/{orderId}/status")
    public void updateOrderStatus(@PathVariable Long orderId,
                                  @RequestBody UpdateOrderStatusDTO dto) {
        orderService.updateOrderStatus(orderId, dto);
    }

    @DeleteMapping("/orders/delete/{orderId}")
    public void deleteOrder(@PathVariable Long orderId) {
        orderService.softDeleteOrder(orderId);
    }
}
