package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.IdempotentRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdempotentRequestRepository
        extends JpaRepository<IdempotentRequest, String> {
}
