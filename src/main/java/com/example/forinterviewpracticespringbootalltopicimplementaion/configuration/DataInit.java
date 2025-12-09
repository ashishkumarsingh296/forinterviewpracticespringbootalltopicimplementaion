package com.example.forinterviewpracticespringbootalltopicimplementaion.configuration;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Product;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Role;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.User;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.ProductRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Set;

@Configuration
public class DataInit {

    @Bean
    CommandLineRunner init(UserRepository repo, PasswordEncoder encoder, ProductRepository productRepository) {
        return args -> {
            if (!repo.existsByEmail("admin@example.com")) {
                repo.save(User.builder()
                    .email("admin@example.com")
                    .name("Admin")
                    .password(encoder.encode("Admin@123"))
                    .roles(Set.of(Role.ROLE_ADMIN))
                    .build());
            }
            if (productRepository.count() > 0) {
                return;
            }

            List<Product> products = List.of(
                    new Product(null, "Laptop", "High performance laptop", 55000.0, 10, true),
                    new Product(null, "Mobile Phone", "Android smartphone", 18000.0, 25, true),
                    new Product(null, "Headphones", "Noise cancelling headphones", 3000.0, 40, true),
                    new Product(null, "Keyboard", "Mechanical keyboard", 1500.0, 30, true),
                    new Product(null, "Mouse", "Wireless mouse", 800.0, 50, true)
            );

            productRepository.saveAll(products);

            System.out.println("✅ Default Products Inserted Successfully...");
        };
    }
}
