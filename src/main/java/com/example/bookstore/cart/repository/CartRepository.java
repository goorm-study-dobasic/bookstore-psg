package com.example.bookstore.cart.repository;

import com.example.bookstore.cart.domain.Cart;
import com.example.bookstore.inventory.domain.Inventory;
import com.example.bookstore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

    boolean existsByUserAndInventory(User user, Inventory inventory);

    Cart findByUserAndInventory(User user, Inventory inventory); // 추가
}
