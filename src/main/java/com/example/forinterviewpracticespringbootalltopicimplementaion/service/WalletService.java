package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.WalletTransaction;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.UserRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.WalletRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.WalletTransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {



//    @Transactional
//    public void creditWallet(Long userId, Double amount, String reference) {
//        Wallet wallet = getOrCreateWalletForUser(userId);
//        wallet.setBalance(wallet.getBalance() + amount);
//        walletRepository.save(wallet);
//
//        WalletTransaction tx = WalletTransaction.builder()
//                .wallet(wallet)
//                .amount(amount)
//                .type("CREDIT")
//                .reference(reference != null ? reference : "CREDIT_" + UUID.randomUUID())
//                .build();
//        walletTransactionRepository.save(tx);
//    }


    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    public void debit(Long userId, Double amount, String ref) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));

        if (wallet.getBalance() < amount) {
            throw new IllegalStateException("Insufficient balance");
        }

        wallet.setBalance(wallet.getBalance() - amount);

        walletTransactionRepository.save(
                WalletTransaction.builder()
                        .wallet(wallet)
                        .type("DEBIT")
                        .amount(amount)
                        .reference(ref)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
    }



}
