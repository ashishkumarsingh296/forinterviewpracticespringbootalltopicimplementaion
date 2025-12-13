package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Invoice;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.InvoiceRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final OrderRepository orderRepo;
    private final InvoiceRepository invoiceRepo;

//    public byte[] generateInvoice(Long orderId) {
//
//        Order order = orderRepo.findById(orderId)
//                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
//
//        String invoiceId = UUID.randomUUID().toString();
//
//        String path = "C:\\Users\\ASHISH\\Downloads\\" + invoiceId + ".pdf";
//
//        createPdf(order, path);
//
//        Invoice invoice = new Invoice();
//        invoice.setInvoiceId(invoiceId);
//        invoice.setOrderId(orderId.toString());
//        invoice.setTotalAmount(order.getTotalAmount());
//        invoice.setPdfPath(path);
//        invoice.setGeneratedAt(LocalDateTime.now());
//
//        invoiceRepo.save(invoice);
//        return null;
//    }

    public byte[] generateInvoice(Long orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        String invoiceId = UUID.randomUUID().toString();
        String path = "C:\\Users\\ASHISH\\Downloads\\" + invoiceId + ".pdf";

        createPdf(order, path);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(invoiceId);
        invoice.setOrderId(orderId.toString());
        invoice.setTotalAmount(order.getTotalAmount());
        invoice.setPdfPath(path);
        invoice.setGeneratedAt(LocalDateTime.now());
        invoiceRepo.save(invoice);

        try {
            return java.nio.file.Files.readAllBytes(
                    java.nio.file.Paths.get(path)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to read invoice pdf", e);
        }
    }

    private void createPdf(Order order, String path) {

        try (PDDocument doc = new PDDocument()) {

            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
            cs.newLineAtOffset(50, 750);
            cs.showText("INVOICE");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 12);
            cs.newLineAtOffset(50, 720);
            cs.showText("Order#: " + order.getId());
            cs.endText();

            cs.beginText();
            cs.newLineAtOffset(50, 700);
            cs.showText("Date: " + LocalDateTime.now());
            cs.endText();

            cs.beginText();
            cs.newLineAtOffset(50, 660);
            cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
            cs.showText("Total Amount: " + order.getTotalAmount());
            cs.endText();

            cs.close();
            doc.save(path);

        } catch (Exception e) {
            throw new RuntimeException("Invoice PDF generation failed", e);
        }
    }
}
