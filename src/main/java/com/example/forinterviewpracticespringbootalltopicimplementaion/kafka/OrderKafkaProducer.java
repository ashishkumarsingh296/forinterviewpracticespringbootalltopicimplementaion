package com.example.forinterviewpracticespringbootalltopicimplementaion.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaProducer {

    private final KafkaTemplate<String, Object> kafka;

    public OrderKafkaProducer(KafkaTemplate<String, Object> kafka) { this.kafka = kafka; }

    public void publishOrderCreated(Object payload) {
        kafka.send("orders.created", payload);
    }
}
