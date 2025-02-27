package com.example.bookstore.order.dto;

import com.example.bookstore.order.domain.Order;
import com.example.bookstore.order.domain.OrderStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDto {

    private Long orderId;
    private OrderStatus status;
    private LocalDateTime createdAt;
    private Long deliveryId;

    public static OrderDto from(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .deliveryId(order.getDelivery().getId())
                .build();
    }
}
