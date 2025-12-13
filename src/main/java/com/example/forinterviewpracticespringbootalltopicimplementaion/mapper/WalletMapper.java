package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletTransactionDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.WalletTransaction;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletDTO toDTO(Wallet wallet) {
        if (wallet == null) return null;

        WalletDTO dto = new WalletDTO();
        dto.setWalletId(wallet.getId());
        dto.setUserId(wallet.getUserId() != null ? wallet.getUserId() : null);
        dto.setBalance(wallet.getBalance());

        return dto;
    }

    public WalletTransactionDTO toDTO(WalletTransaction walletTransaction ) {
        if (walletTransaction == null) return null;

        WalletTransactionDTO dto = new WalletTransactionDTO();
        dto.setTransactionId(Long.valueOf(walletTransaction.getId()));
        dto.setType(String.valueOf(walletTransaction.getType()));
        dto.setAmount(walletTransaction.getAmount());
        dto.setReference(walletTransaction.getReference());
        dto.setCreatedAt(walletTransaction.getCreatedAt());
        return dto;
    }
}
