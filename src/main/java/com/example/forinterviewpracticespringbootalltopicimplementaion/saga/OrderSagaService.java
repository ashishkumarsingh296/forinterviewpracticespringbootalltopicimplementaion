package com.example.forinterviewpracticespringbootalltopicimplementaion.saga;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderSaga;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderStatus;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.SagaStatus;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderSagaRepository;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.InvoiceService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.PaymentService;
import com.example.forinterviewpracticespringbootalltopicimplementaion.service.WalletService;
import jakarta.transaction.Transactional;
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
    public void executeOrderSaga(Long orderId, String idempotencyKey) throws Exception {

        Order order = orderRepo.findById(orderId)
                .orElseThrow();

        OrderSaga saga = OrderSaga.builder()
                .sagaId(UUID.randomUUID().toString())
                .orderId(orderId)
                .status(SagaStatus.STARTED)
                .createdAt(LocalDateTime.now())
                .build();

        sagaRepo.save(saga);

        try {
            walletService.debit(
                    String.valueOf(order.getUser().getId()),
                    order.getTotalAmount(),
                    "ORDER_" + orderId
            );
            saga.setStatus(SagaStatus.WALLET_DEBITED);
            sagaRepo.save(saga);

            paymentService.pay(idempotencyKey, orderId);
            saga.setStatus(SagaStatus.PAYMENT_DONE);
            sagaRepo.save(saga);

            invoiceService.generateInvoice(orderId);
            saga.setStatus(SagaStatus.INVOICE_GENERATED);
            sagaRepo.save(saga);

            order.setOrderStatus(OrderStatus.COMPLETED);
            orderRepo.save(order);

            saga.setStatus(SagaStatus.COMPLETED);
            sagaRepo.save(saga);

        } catch (Exception ex) {
            compensate(order);
            saga.setStatus(SagaStatus.FAILED);
            sagaRepo.save(saga);
            throw ex;
        }
    }

    private void compensate(Order order) {
        walletService.credit(
                String.valueOf(order.getUser().getId()),
                order.getTotalAmount(),
                "REFUND_ORDER_" + order.getId()
        );
        order.setOrderStatus(OrderStatus.FAILED);
    }

    public void startSaga(Long orderId) {
        OrderSaga saga = new OrderSaga();
        saga.setOrderId(orderId);
        saga.setStatus(SagaStatus.STARTED);
        sagaRepo.save(saga);
    }

}
