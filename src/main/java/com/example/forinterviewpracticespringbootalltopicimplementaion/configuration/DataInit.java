package com.example.forinterviewpracticespringbootalltopicimplementaion.configuration;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Role;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.User;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder) {
        return args -> {
            if (!repo.existsByEmail("admin@example.com")) {
                repo.save(User.builder()
                    .email("admin@example.com")
                    .name("Admin")
                    .password(encoder.encode("Admin@123"))
                    .roles(Set.of(Role.ROLE_ADMIN))
                    .build());
            }
        };
    }
}
