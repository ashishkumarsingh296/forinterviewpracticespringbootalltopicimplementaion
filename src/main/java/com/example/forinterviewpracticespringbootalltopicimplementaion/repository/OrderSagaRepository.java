package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.OrderSaga;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.SagaStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSagaRepository extends JpaRepository<OrderSaga, String> {
    List<OrderSaga> findByStatusIn(List<SagaStatus> statuses);

}
