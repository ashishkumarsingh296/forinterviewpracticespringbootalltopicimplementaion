package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletTransactionDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.WalletTransaction;
import org.springframework.stereotype.Component;

@Component
public class WalletTransactionMapper {

    public WalletTransactionDTO toDTO(WalletTransaction walletTransaction ) {
        if (walletTransaction == null) return null;

        WalletTransactionDTO dto = new WalletTransactionDTO();
        dto.setTransactionId(walletTransaction.getId());
        dto.setType(walletTransaction.getType());
        dto.setAmount(walletTransaction.getAmount());
        dto.setReference(walletTransaction.getReference());
        dto.setCreatedAt(walletTransaction.getCreatedAt());
        return dto;
    }
}
