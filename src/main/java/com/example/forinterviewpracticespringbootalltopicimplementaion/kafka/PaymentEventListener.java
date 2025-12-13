package com.example.forinterviewpracticespringbootalltopicimplementaion.kafka;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.PaymentEvent;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventListener {

    @Autowired
    private InvoiceService invoiceService;

    @KafkaListener(topics = "payment-events",
                   groupId = "invoice-group")
    public void consume(PaymentEvent event) {
        if ("PAYMENT_SUCCESS".equals(event.getEventType())) {
            invoiceService.generateInvoice(Long.valueOf(event.getOrderId()));
        }
    }
}
