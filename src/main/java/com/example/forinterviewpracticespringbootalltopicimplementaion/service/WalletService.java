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
    public void credit(String userId, Double amount) {

        // 1️⃣ Wallet row lock karo
        Wallet wallet = walletRepository
                .findTopByUserIdOrderByIdDesc(userId)
                .orElseGet(() -> {
                    Wallet w = new Wallet();
                    w.setUserId(userId);
                    w.setBalance(0.0);
                    return walletRepository.save(w);
                });

        // 2️⃣ Balance update
        double newBalance = wallet.getBalance() + amount;
        wallet.setBalance(newBalance);
        walletRepository.save(wallet);

        // 3️⃣ Transaction history
        WalletTransaction tx = new WalletTransaction();
        tx.setUserId(userId);
        tx.setAmount(amount);
        tx.setType(TxType.CREDIT);
        tx.setBalance(newBalance);
        tx.setReference("CREDIT");

        walletTransactionRepository.save(tx);

    }
    
    @Transactional
    public void debit(String userId, Double amount) {

        // 1️⃣ Get latest wallet balance
        Wallet wallet = walletRepository.findTopByUserIdOrderByIdDesc(userId)
                .orElseThrow(() -> new IllegalStateException("Wallet not found"));

        // 2️⃣ Balance check
        if (wallet.getBalance() < amount) {
            throw new IllegalStateException("Insufficient wallet balance");
        }

        // 3️⃣ Calculate new balance
        Double newBalance = wallet.getBalance() - amount;

        // 4️⃣ Save wallet snapshot
        Wallet updatedWallet = Wallet.builder()
                .userId(wallet.getUserId())
                .balance(newBalance)
                .build();

        walletRepository.save(updatedWallet);

        // 5️⃣ Save transaction (ledger)
        WalletTransaction tx = new WalletTransaction();
        tx.setId(UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE);
        tx.setUserId(String.valueOf(userId));
        tx.setAmount(amount);
        tx.setType(TxType.DEBIT);
        tx.setBalance(newBalance);
        tx.setReference("PAYMENT");
        tx.setCreatedAt(LocalDateTime.now());

        walletTransactionRepository.save(tx);
    }
}
