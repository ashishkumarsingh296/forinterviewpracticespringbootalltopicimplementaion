package com.example.forinterviewpracticespringbootalltopicimplementaion.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String entityName;
    private String actionType; // CREATE, UPDATE, DELETE
    private String details;

    @CreationTimestamp
    private LocalDateTime timestamp;

    private Long targetEntityId;

}
