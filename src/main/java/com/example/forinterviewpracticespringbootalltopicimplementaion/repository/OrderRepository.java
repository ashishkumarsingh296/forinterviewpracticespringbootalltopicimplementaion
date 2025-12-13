package com.example.forinterviewpracticespringbootalltopicimplementaion.repository;

import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByUserIdAndIsDeletedFalse(Long userId, Pageable pageable);
    @Query("""
    select distinct o from Order o
    join fetch o.user
    join fetch o.items i
    join fetch i.product
    where o.id = :orderId
""")
    Optional<Order> findOrderForInvoice(@Param("orderId") Long orderId);


}
