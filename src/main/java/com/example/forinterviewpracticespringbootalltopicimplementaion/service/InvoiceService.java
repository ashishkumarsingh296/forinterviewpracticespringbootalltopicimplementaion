package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Invoice;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderItem;
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
    public byte[] generateInvoice(Long orderId) {
        Order order = orderRepo.findOrderForInvoice(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));
//        Order order = orderRepo.findById(orderId)
//                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        String invoiceId = UUID.randomUUID().toString();
        String path = "C:\\Users\\ASHISH\\Downloads\\" + invoiceId + ".pdf";

        createPdf(order,invoiceId, path);

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

    /**
         * Generates invoice PDF and stores metadata
         */

        /**
         * REAL invoice PDF generation
         */
        private void createPdf(Order order, String invoiceId, String path) {

            try (PDDocument doc = new PDDocument()) {

                PDPage page = new PDPage(PDRectangle.A4);
                doc.addPage(page);

                PDPageContentStream cs = new PDPageContentStream(doc, page);

                // ================= HEADER =================
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 20);
                cs.newLineAtOffset(50, 770);
                cs.showText("INVOICE");
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 12);
                cs.newLineAtOffset(50, 740);
                cs.showText("Invoice ID: " + invoiceId);
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(50, 720);
                cs.showText("Order ID: " + order.getId());
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(50, 700);
                cs.showText("Date: " + LocalDateTime.now());
                cs.endText();

                // ================= CUSTOMER =================
                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.newLineAtOffset(50, 670);
                cs.showText("Billed To:");
                cs.endText();

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA, 11);
                cs.newLineAtOffset(50, 650);
                cs.showText(order.getUser().getEmail());
                cs.endText();

                // ================= TABLE HEADER =================
                float y = 610;

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 11);
                cs.newLineAtOffset(50, y);
                cs.showText("Item");
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(250, y);
                cs.showText("Qty");
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(300, y);
                cs.showText("Price");
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(380, y);
                cs.showText("Total");
                cs.endText();

                y -= 20;
                cs.setFont(PDType1Font.HELVETICA, 11);

                double subtotal = 0.0;

                // ================= ITEMS =================
                for (OrderItem item : order.getItems()) {

                    double itemTotal = item.getPrice() * item.getQuantity();
                    subtotal += itemTotal;

                    cs.beginText();
                    cs.newLineAtOffset(50, y);
                    cs.showText(item.getProduct().getName());
                    cs.endText();

                    cs.beginText();
                    cs.newLineAtOffset(250, y);
                    cs.showText(item.getQuantity().toString());
                    cs.endText();

                    cs.beginText();
                    cs.newLineAtOffset(300, y);
                    cs.showText("Rs. " + item.getPrice());
                    cs.endText();

                    cs.beginText();
                    cs.newLineAtOffset(380, y);
                    cs.showText("Rs. " + itemTotal);
                    cs.endText();

                    y -= 15;
                }

                // ================= TOTALS =================
                double gst = subtotal * 0.18;
                double grandTotal = subtotal + gst;

                y -= 20;

                cs.beginText();
                cs.newLineAtOffset(300, y);
                cs.showText("Subtotal:");
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(380, y);
                cs.showText("Rs. " + subtotal);
                cs.endText();

                y -= 15;

                cs.beginText();
                cs.newLineAtOffset(300, y);
                cs.showText("GST (18%):");
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(380, y);
                cs.showText("Rs. " + gst);
                cs.endText();

                y -= 20;

                cs.beginText();
                cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
                cs.newLineAtOffset(300, y);
                cs.showText("Total Payable:");
                cs.endText();

                cs.beginText();
                cs.newLineAtOffset(380, y);
                cs.showText("Rs. " + grandTotal);
                cs.endText();

                cs.close();
                doc.save(path);

            } catch (Exception e) {
                throw new RuntimeException("Invoice PDF generation failed", e);
            }
        }
    }

