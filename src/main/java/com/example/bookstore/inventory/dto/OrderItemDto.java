package com.example.bookstore.inventory.dto;

import com.example.bookstore.order.domain.OrderItem;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderItemDto {

    private Long orderItemId;
    private InventoryDtoForUser inventoryDtoForUser;
    private int quantity;
    private int price;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static OrderItemDto from(OrderItem orderItem) {
        return OrderItemDto.builder()
                .orderItemId(orderItem.getOrderItemId())
                .inventoryDtoForUser(InventoryDtoForUser.from(orderItem.getInventory()))
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice())
                .createdAt(orderItem.getCreatedAt())
                .lastModifiedAt(orderItem.getLastModifiedAt())
                .build();
    }
}
