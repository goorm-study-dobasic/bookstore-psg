package com.example.bookstore.delivery.repository;

import com.example.bookstore.delivery.domain.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    Optional<Delivery> findDeliveryById(Long id);
}
