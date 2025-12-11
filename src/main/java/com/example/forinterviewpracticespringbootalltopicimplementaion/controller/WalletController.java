package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Wallet;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.WalletTransaction;
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

    @GetMapping("/wallet/{userId}")
    public Wallet getWallet(@PathVariable Long userId) {
        return walletService.getOrCreateWalletForUser(userId);
    }

    @PostMapping("/wallet/{userId}/credit")
    public void creditWallet(@PathVariable Long userId, @RequestParam Double amount) {
        walletService.creditWallet(userId, amount, null);
    }

    @PostMapping("/wallet/{userId}/debit")
    public void debitWallet(@PathVariable Long userId, @RequestParam Double amount) {
        walletService.debitWallet(userId, amount, null);
    }

    @GetMapping("/wallet/{userId}/transactions")
    public Page<WalletTransaction> getTransactions(@PathVariable Long userId,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return walletService.getWalletTransactions(userId, PageRequest.of(page, size));
    }
}
