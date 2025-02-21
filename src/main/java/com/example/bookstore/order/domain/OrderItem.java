package com.example.bookstore.order.domain;

import com.example.bookstore.inventory.domain.Inventory;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int quantity;
    private int price;

    @Column(updatable = false)
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public OrderItem(Order order, Inventory inventory, int quantity) {
        this.order = order;
        this.inventory = inventory;
        this.quantity = quantity;
        this.price = inventory.totalPrice(quantity);
        this.createdAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }
}
