package com.example.bookstore.order.repository;

import com.example.bookstore.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT o FROM OrderItem o JOIN FETCH o.order WHERE o.order.orderId = :orderId")
    List<OrderItem> findByOrderId(Long orderId);
}
