package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.IdempotentRequest;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, String> {


}
