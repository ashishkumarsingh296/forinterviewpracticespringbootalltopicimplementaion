package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderItemDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderItem;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WalletMapper {

    public WalletDTO toDTO(WalletDTO wallet) {
        if (wallet == null) return null;

        WalletDTO dto = new WalletDTO();
        dto.setWalletId(wallet.getWalletId());
        dto.setUserId(wallet.getUserId() != null ? wallet.getUserId() : null);
        dto.setBalance(wallet.getBalance());

        return dto;
    }

}
