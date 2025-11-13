package com.example.forinterviewpracticespringbootalltopicimplementaion.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AppLogger {

    private static final Logger log = LoggerFactory.getLogger(AppLogger.class);

    public void info(String message) {
        log.info(message);
    }

    public void warn(String message) {
        log.warn(message);
    }

    public void error(String message, Exception e) {
        log.error(message, e);
    }

    public void debug(String message) {
        log.debug(message);
    }
}
