package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> { }
