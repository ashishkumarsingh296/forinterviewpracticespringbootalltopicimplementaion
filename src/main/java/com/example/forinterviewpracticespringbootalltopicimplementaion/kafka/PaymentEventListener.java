package com.example.forinterviewpracticespringbootalltopicimplementaion.kafka;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.PaymentEvent;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.InvoiceService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PaymentEventListener {

    @Autowired
    private InvoiceService invoiceService;

//    @KafkaListener(topics = "payment-events",
//                   groupId = "invoice-group")
//    public void consume(PaymentEvent event) {
//        if ("PAYMENT_SUCCESS".equals(event.getEventType())) {
//            invoiceService.generateInvoice(Long.valueOf(event.getOrderId()));
//        }
//    }

    @KafkaListener(topics = "payment-events", groupId = "invoice-group")
    public void consume(String message) throws JsonProcessingException {

        try {
            ObjectMapper mapper = new ObjectMapper();
            PaymentEvent event =
                    mapper.readValue(message, PaymentEvent.class);

            log.info("Received Payment Event: {}", event);

            // Call invoice / notification logic here

        } catch (Exception ex) {
            log.error("Failed to process payment event", ex);
            throw ex; // important for retry / DLQ
        }
    }

}
