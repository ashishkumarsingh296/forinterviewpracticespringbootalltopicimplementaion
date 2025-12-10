package com.example.forinterviewpracticespringbootalltopicimplementaion.controller;

import com.example.forinterviewpracticespringbootalltopicimplementaion.service.InvoiceService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.forinterviewpracticespringbootalltopicimplementaion.constants.ApiPathConstants.BASE_PATH;

@RestController
@RequestMapping(BASE_PATH)
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping("/invoices/{orderId}")
    public ResponseEntity<byte[]> getInvoice(@PathVariable Long orderId) throws Exception {
        byte[] pdf = invoiceService.generateInvoicePdf(orderId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "invoice-" + orderId + ".pdf");

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
