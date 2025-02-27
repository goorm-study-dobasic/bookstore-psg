package com.example.bookstore.cart.repository;

import com.example.bookstore.cart.domain.Cart;
import com.example.bookstore.inventory.domain.Inventory;
import com.example.bookstore.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {

    List<Cart> findByUser(User user);

    boolean existsByUserAndInventory(User user, Inventory inventory);

    Cart findByUserAndInventory(User user, Inventory inventory); // 추가

    List<Cart> findCartsByCartIdIn(Collection<Long> cartIds);
}
