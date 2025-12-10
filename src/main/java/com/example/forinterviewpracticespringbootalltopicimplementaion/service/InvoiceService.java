package com.example.forinterviewpracticespringbootalltopicimplementaion.service;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final OrderRepository orderRepository;

    public byte[] generateInvoicePdf(Long orderId) throws Exception {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        try (PDDocument doc = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);

            PDPageContentStream cs = new PDPageContentStream(doc, page);

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA_BOLD, 18);
            cs.newLineAtOffset(50, 800);
            cs.showText("INVOICE");
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 12);
            cs.newLineAtOffset(50, 770);
            cs.showText("Order#: " + order.getOrderNumber());
            cs.endText();

            cs.beginText();
            cs.setFont(PDType1Font.HELVETICA, 12);
            cs.newLineAtOffset(50, 750);
            cs.showText("Date: " + order.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            cs.endText();

            float y = 720;
            cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
            cs.beginText();
            cs.newLineAtOffset(50, y);
            cs.showText("Product");
            cs.newLineAtOffset(200, 0);
            cs.showText("Qty");
            cs.newLineAtOffset(50, 0);
            cs.showText("Price");
            cs.endText();

            cs.setFont(PDType1Font.HELVETICA, 12);
            y -= 20;
            for (var item : order.getItems()) {
                cs.beginText();
                cs.newLineAtOffset(50, y);
                cs.showText(item.getProduct().getName());
                cs.newLineAtOffset(200, 0);
                cs.showText(String.valueOf(item.getQuantity()));
                cs.newLineAtOffset(50, 0);
                cs.showText(String.valueOf(item.getPrice()));
                cs.endText();
                y -= 20;
            }

            cs.beginText();
            cs.newLineAtOffset(50, y - 10);
            cs.setFont(PDType1Font.HELVETICA_BOLD, 12);
            cs.showText("Total: " + order.getTotalAmount());
            cs.endText();

            cs.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            doc.save(baos);
            return baos.toByteArray();
        }
    }
}
