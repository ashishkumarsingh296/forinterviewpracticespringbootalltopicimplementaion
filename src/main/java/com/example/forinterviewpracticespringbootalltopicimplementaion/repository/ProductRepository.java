package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
