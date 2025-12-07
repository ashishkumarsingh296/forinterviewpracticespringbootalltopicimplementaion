//package com.example.forinterviewpracticespringbootalltopicimplementaion.configuration;
//
//import jakarta.annotation.PreDestroy;
//import org.springframework.context.annotation.Configuration;
//import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class MySQLCleanupConfig {
//
//    @PreDestroy
//    public void shutdownMySQLCleanupThread() {
//        try {
//            AbandonedConnectionCleanupThread.checkedShutdown();
//        } catch (Exception e) {
//            System.out.println("MySQL cleanup thread shutdown failed: " + e.getMessage());
//        }
//    }
//}
