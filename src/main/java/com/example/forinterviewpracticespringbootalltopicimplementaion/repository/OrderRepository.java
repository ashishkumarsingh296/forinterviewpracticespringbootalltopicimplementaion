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
    select o from Order o
    join fetch o.user
    where o.id = :orderId
""")
    Optional<Order> findByIdWithUser(@Param("orderId") Long orderId);

}
