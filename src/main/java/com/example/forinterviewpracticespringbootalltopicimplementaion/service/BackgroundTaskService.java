package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BackgroundTaskService {

    @Async
    public void sendEmailAsync(String to, String message) {
        log.info("Sending email to {} ...", to);
        try {
            Thread.sleep(3000); // simulate delay
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        log.info("✅ Email sent to {}", to);
    }
}
