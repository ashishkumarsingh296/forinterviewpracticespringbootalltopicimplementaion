package com.example.forinterviewpracticespringbootalltopicimplementaion.mapper;

import com.example.forinterviewpracticespringbootalltopicimplementaion.dto.OrderDTO;
import com.example.forinterviewpracticespringbootalltopicimplementaion.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
public interface OrderMapper {

    @Mapping(target = "userId", expression = "java(order.getUser() != null ? order.getUser().getId() : null)")
    @Mapping(target = "paymentStatus", expression = "java(order.getPayment() != null ? order.getPayment().getStatus() : null)")
    @Mapping(target = "paymentMethod", expression = "java(order.getPayment() != null ? order.getPayment().getPaymentMethod() : null)")
    OrderDTO toDTO(Order order);
}
