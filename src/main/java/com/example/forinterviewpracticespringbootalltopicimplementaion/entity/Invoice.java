package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
@Data
public class Invoice {

    @Id
    private String invoiceId;

    private String orderId;

    private Double totalAmount;

    private String pdfPath;

    private LocalDateTime generatedAt;
}
