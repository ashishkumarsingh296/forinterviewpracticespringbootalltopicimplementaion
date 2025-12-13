package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.WalletTransactionDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.WalletTransaction;
import com.example.forinterviewpracticespringbootalltopicimplementaion.mapper.WalletMapper;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;
    private final WalletMapper walletMapper;
    @PostMapping("/wallet/{userId}/debit")
    public void debitWallet(@PathVariable Long userId, @RequestParam Double amount) {
        walletService.credit(userId, amount);
    }

}
