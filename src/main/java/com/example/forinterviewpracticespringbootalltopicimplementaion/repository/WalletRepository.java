package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;



@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Query("""
        SELECT w FROM Wallet w
        WHERE w.userId = :userId
        ORDER BY w.createdAt DESC
        LIMIT 1
    """)
    Optional<Wallet> findLastBalance(@RequestParam("userId") String userId);
}

