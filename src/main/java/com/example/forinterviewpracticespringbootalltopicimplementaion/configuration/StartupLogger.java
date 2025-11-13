package com.example.forinterviewpracticespringbootalltopicimplementaion.configuration;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

@Slf4j
@Component
public class StartupLogger {

    private final Environment environment;

    @Value("${spring.application.name:MyApp}")
    private String appName;

    public StartupLogger(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void logAppStartup() throws UnknownHostException {
        String port = environment.getProperty("local.server.port", "8080");
        String profile = Arrays.toString(environment.getActiveProfiles());
        String hostAddress = InetAddress.getLocalHost().getHostAddress();

        log.info("\n----------------------------------------------------------");
        log.info(" 🚀 {} is running!", appName);
        log.info(" 🌍 Host: {}", hostAddress);
        log.info(" 🔌 Port: {}", port);
        log.info(" 🧩 Active Profiles: {}", profile);
        log.info("----------------------------------------------------------");
    }
}
