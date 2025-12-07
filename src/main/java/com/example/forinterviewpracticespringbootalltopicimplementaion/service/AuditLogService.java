package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.AuditLog;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuditLogService {

    private final AuditLogRepository auditLogRepository;

    public void log(String username, String entityName, String actionType, Boolean isDeleted,String details, Long targetEntityId) {
        AuditLog log = AuditLog.builder()
                .username(username)
                .entityName(entityName)
                .actionType(actionType)
                .isDeleted(true)
                .details(details)
                .targetEntityId(targetEntityId)
                .build();

        auditLogRepository.save(log);
    }

    public List<AuditLog> getAllLogs() {
        return auditLogRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

//    public List<AuditLog> findByUsername(String username) {
//        return auditLogRepository.findByUsernameIgnoreCase(username);
//    }
//
//    public List<AuditLog> findByEntityName(String entityName) {
//        return auditLogRepository.findByEntityNameIgnoreCase(entityName);
//    }

    public void deleteAll() {
        auditLogRepository.deleteAll();
    }
}
