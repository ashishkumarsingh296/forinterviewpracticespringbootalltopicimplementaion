package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import org.mapstruct.Mapper;

package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import org.springframework.stereotype.Component;

@Component
public class WalletMapper {

    public WalletDTO toDTO(Wallet wallet) {
        if (wallet == null) return null;
        WalletDTO dto = new WalletDTO();
        dto.setUserId(wallet.getUser() != null ? wallet.getUser().getId() : null);
        dto.setBalance(wallet.getBalance());
        dto.setStatus(wallet.getStatus());
        return dto;
    }
}

