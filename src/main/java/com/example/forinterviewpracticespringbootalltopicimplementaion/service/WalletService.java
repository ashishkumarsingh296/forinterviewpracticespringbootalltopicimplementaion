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

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Wallet getOrCreateWalletForUser(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseGet(() -> {
                    var user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
                    Wallet w = Wallet.builder().user(user).balance(0.0).build();
                    return walletRepository.save(w);
                });
    }

    @Transactional
    public void creditWallet(Long userId, Double amount, String reference) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        Wallet wallet = getOrCreateWalletForUser(userId);
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        WalletTransaction tx = WalletTransaction.builder()
                .wallet(wallet)
                .amount(amount)
                .type("CREDIT")
                .reference(reference != null ? reference : "CREDIT_" + UUID.randomUUID())
                .build();
        walletTransactionRepository.save(tx);
    }

    @Transactional
    public void debitWallet(Long userId, Double amount, String reference) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be positive");
        Wallet wallet = getOrCreateWalletForUser(userId);
        if (wallet.getBalance() < amount) throw new IllegalStateException("Insufficient wallet balance");
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);

        WalletTransaction tx = WalletTransaction.builder()
                .wallet(wallet)
                .amount(-amount)
                .type("DEBIT")
                .reference(reference != null ? reference : "DEBIT_" + UUID.randomUUID())
                .build();
        walletTransactionRepository.save(tx);
    }

    public Page<WalletTransaction> getWalletTransactions(Long userId, Pageable pageable) {
        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new EntityNotFoundException("Wallet not found"));
        return walletTransactionRepository.findByWalletId(wallet.getId(), pageable);
    }
}
