package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.TxType;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.WalletTransaction;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.WalletRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.WalletTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    @Transactional
    public void credit(Long userId, Double amount) {

        Optional<Wallet> lastWallet =
                walletRepository.findTopByUserIdOrderByIdDesc(userId);

        double previousBalance = lastWallet
                .map(Wallet::getBalance)
                .orElse(0.0);

        WalletTransaction tx = new WalletTransaction();
        tx.setId(Long.valueOf(UUID.randomUUID().toString()));
        tx.setUserId(userId);
        tx.setAmount(amount);
        tx.setType(TxType.CREDIT);
        tx.setBalance(previousBalance + amount);
        tx.setCreatedAt(LocalDateTime.now());

        walletTransactionRepository.save(tx); // ✅ CORRECT
    }



}
