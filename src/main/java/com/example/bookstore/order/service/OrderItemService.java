package com.example.bookstore.order.service;

import com.example.bookstore.inventory.dto.OrderItemDto;
import com.example.bookstore.order.domain.OrderItem;
import com.example.bookstore.order.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public void save(OrderItem orderItem) {
        orderItemRepository.save(orderItem);
    }

    public List<OrderItemDto> findOrderItemDtoByOrder(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems.stream()
                .map(orderItem -> OrderItemDto.from(orderItem))
                .toList();
    }

    public List<OrderItem> findOrderItemByOrder(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        return orderItems;
    }
}
