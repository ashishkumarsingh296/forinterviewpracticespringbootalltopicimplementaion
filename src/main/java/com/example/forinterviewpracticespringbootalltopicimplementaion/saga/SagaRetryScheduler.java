package com.example.forinterviewpracticespringbootalltopicimplementaion.saga;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderSaga;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.SagaStatus;
import com.example.forinterviewpracticespringbootalltopicimplementaion.repository.OrderSagaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SagaRetryScheduler {

    private final OrderSagaRepository sagaRepo;
    private final OrderSagaService sagaService;

    @Scheduled(cron = "0 */5 * * * *") // every 5 min
    public void retryFailedSagas() {

        List<OrderSaga> sagas = sagaRepo.findByStatusIn(
                List.of(SagaStatus.FAILED, SagaStatus.STARTED)
        );

        for (OrderSaga saga : sagas) {
            try {
                sagaService.resumeSaga(saga);
            } catch (Exception ex) {
                // log only, retry again next cycle
            }
        }
    }
}
