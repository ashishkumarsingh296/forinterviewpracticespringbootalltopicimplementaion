package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.WalletTransaction;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.TxType;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.WalletRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;

    @Transactional
    public void debit(String userId, Double amount,String reference) {

        // 1️⃣ Fetch wallet with pessimistic lock
        Wallet wallet = walletRepository.findTopByUserIdOrderByIdDesc(userId)
                .orElseGet(() -> new Wallet(userId, 0.0));

        // 2️⃣ Check balance
        if (wallet.getBalance() < amount) {
            throw new IllegalStateException("Insufficient wallet balance");
        }

        // 3️⃣ Deduct balance and save
        wallet.setBalance(wallet.getBalance() - amount);
        walletRepository.save(wallet);

        // 4️⃣ Record transaction
        WalletTransaction tx = new WalletTransaction();
        tx.setUserId(userId);
        tx.setAmount(amount);
        tx.setReference(reference);
        tx.setType(TxType.DEBIT);
        tx.setBalance(wallet.getBalance());
        tx.setCreatedAt(LocalDateTime.now());
        walletTransactionRepository.save(tx);

    }

    @Transactional
    public void credit(String userId, Double amount,String reference) {

        // 1️⃣ Fetch wallet with pessimistic lock
        Wallet wallet = walletRepository.findTopByUserIdOrderByIdDesc(userId)
                .orElseGet(() -> new Wallet(userId, 0.0));

        // 2️⃣ Add balance and save
        wallet.setBalance(wallet.getBalance() + amount);
        walletRepository.save(wallet);

        // 3️⃣ Record transaction
        WalletTransaction tx = new WalletTransaction();
        tx.setUserId(userId);
        tx.setAmount(amount);
        tx.setReference(reference);
        tx.setType(TxType.CREDIT);
        tx.setBalance(wallet.getBalance());
        tx.setCreatedAt(LocalDateTime.now());
        walletTransactionRepository.save(tx);
    }
}
