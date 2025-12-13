package com.example.forinterviewpracticespringbootalltopicimplementaion.saga;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderSaga;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.SagaStatus;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderSagaRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.InvoiceService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderSagaService {

    private final PaymentService paymentService;
    private final WalletService walletService;
    private final InvoiceService invoiceService;
    private final OrderRepository orderRepo;
    private final OrderSagaRepository sagaRepo;

    @Transactional
    public void executeOrderSaga(Long orderId, String idempotencyKey) {

        String sagaId = UUID.randomUUID().toString();

        OrderSaga saga = new OrderSaga();
        saga.setSagaId(sagaId);
        saga.setOrderId(orderId);
        saga.setStatus(SagaStatus.STARTED);
        saga.setCreatedAt(LocalDateTime.now());
        sagaRepo.save(saga);

        try {
            // 1️⃣ Payment
            paymentService.pay(idempotencyKey, orderId);
            saga.setStatus(SagaStatus.PAYMENT_DONE);
            sagaRepo.save(saga);

            // 2️⃣ Wallet debit
            Order order = orderRepo.findById(orderId).orElseThrow();
            walletService.debit(
                    order.getUser().getId(),
                    order.getTotalAmount(),
                    "ORDER_" + orderId
            );
            saga.setStatus(SagaStatus.WALLET_DEBITED);
            sagaRepo.save(saga);

            // 3️⃣ Invoice
            invoiceService.generateInvoice(orderId);
            saga.setStatus(SagaStatus.INVOICE_GENERATED);

            // 4️⃣ Complete
            saga.setStatus(SagaStatus.COMPLETED);
            sagaRepo.save(saga);

        } catch (Exception ex) {
            compensate(orderId);
            saga.setStatus(SagaStatus.FAILED);
            sagaRepo.save(saga);
            throw ex;
        }
    }

    private void compensate(Long orderId) {
        // Compensation logic
        walletService.creditByOrder(orderId);
        orderRepo.findById(orderId)
                .ifPresent(o -> o.setOrderStatus(OrderStatus.FAILED));
    }
}
