package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.AuditLog;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.AuditLogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AuditLogController {

    private final AuditLogService auditService;

    @GetMapping("/all")
    public ResponseEntity<List<AuditLog>> getAllLogs() {
        return ResponseEntity.ok(auditService.getAllLogs());
    }

//    @GetMapping("/user/{username}")
//    public ResponseEntity<List<AuditLog>> getLogsByUser(@PathVariable String username) {
//        return ResponseEntity.ok(auditService.findByUsername(username));
//    }
//
//    @GetMapping("/entity/{entityName}")
//    public ResponseEntity<List<AuditLog>> getLogsByEntity(@PathVariable String entityName) {
//        return ResponseEntity.ok(auditService.findByEntityName(entityName));
//    }

    @DeleteMapping("/clear")
    public ResponseEntity<String> clearAllLogs() {
        auditService.deleteAll();
        return ResponseEntity.ok("All audit logs cleared successfully.");
    }
}
