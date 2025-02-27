package com.example.bookstore.delivery.service;

import com.example.bookstore.delivery.domain.Delivery;
import com.example.bookstore.delivery.dto.DeliveryDto;
import com.example.bookstore.delivery.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;

    public DeliveryDto getDeliveryDto(Long id) {
        Delivery delivery = deliveryRepository.findDeliveryById(id)
                .orElseThrow(() -> new NoSuchElementException());

        return DeliveryDto.from(delivery);
    }
}
