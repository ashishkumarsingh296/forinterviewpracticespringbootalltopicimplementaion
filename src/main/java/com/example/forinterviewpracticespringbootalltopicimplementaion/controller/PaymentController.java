package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.CreatePaymentRequestDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.saga.OrderSagaService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final OrderSagaService orderSagaService;


    @PostMapping("/payments/pay")
    public ResponseEntity<String> pay(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @RequestBody CreatePaymentRequestDTO dto) throws Exception {

//        return ResponseEntity.ok(
//                paymentService.pay(idempotencyKey, Long.valueOf(String.valueOf(dto.getOrderId())))
//        );

        orderSagaService.executeOrderSaga(
                dto.getOrderId(),
                idempotencyKey
        );

        return ResponseEntity.ok("Order processed via Saga");
    }
}
