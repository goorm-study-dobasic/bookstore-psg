package com.example.bookstore.cart.domain;

import com.example.bookstore.inventory.domain.Inventory;
import com.example.bookstore.user.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id")
    private Inventory inventory;

    private int quantity;

    @Column(updatable = false)
    private LocalDateTime addedAt;

    private LocalDateTime lastModifiedAt;

    public Cart(User user, Inventory inventory, int quantity) {
        this.user = user;
        this.inventory = inventory;
        this.quantity = quantity;
        this.addedAt = LocalDateTime.now();
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void addQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
        this.lastModifiedAt = LocalDateTime.now();
    }
}
