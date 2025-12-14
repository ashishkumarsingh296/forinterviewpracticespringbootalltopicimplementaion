package com.example.forinterviewpracticespringbootalltopicimplementaion.kafka;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.PaymentEvent;
import com.example.forinterviewpracticespringbootalltopicimplementaion.saga.OrderSagaService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventListener {

    @Autowired
    private InvoiceService invoiceService;

    @Autowired
    private OrderSagaService sagaService;

    @KafkaListener(topics = "payment-events",
                   groupId = "invoice-group")
    @Transactional
    public void consume(PaymentEvent event) {
        log.info("Received payment event: {}", event);
        log.info("Skipping invoice generation - handled by Saga");


//        if ("PAYMENT_SUCCESS".equals(event.getEventType())) {
//            //commenting for avoiding duplicate invoices genrated in db from saga and kafka so for now i m just commenting and calling from saga
////            invoiceService.generateInvoice(Long.valueOf(event.getOrderId()));
//        }

    }

}
