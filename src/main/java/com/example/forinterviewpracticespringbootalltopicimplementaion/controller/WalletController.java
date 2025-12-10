package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/{userId}")
    public ResponseEntity<?> getWallet(@PathVariable Long userId) {
        var wallet = walletService.getOrCreateWalletForUser(userId);
        return ResponseEntity.ok(wallet);
    }

    @PostMapping("/{userId}/credit")
    public ResponseEntity<?> credit(@PathVariable Long userId, @RequestParam Double amount, @RequestParam(required = false) String ref) {
        walletService.creditWallet(userId, amount, ref);
        return ResponseEntity.ok("Credited");
    }

    @PostMapping("/{userId}/debit")
    public ResponseEntity<?> debit(@PathVariable Long userId, @RequestParam Double amount, @RequestParam(required = false) String ref) {
        walletService.debitWallet(userId, amount, ref);
        return ResponseEntity.ok("Debited");
    }

    @GetMapping("/{userId}/transactions")
    public ResponseEntity<Page<?>> txs(@PathVariable Long userId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        Page<?> p = walletService.getWalletTransactions(userId, PageRequest.of(page, size));
        return ResponseEntity.ok(p);
    }
}
