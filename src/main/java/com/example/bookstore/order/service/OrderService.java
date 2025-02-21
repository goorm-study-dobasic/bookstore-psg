package com.example.bookstore.order.service;

import com.example.bookstore.cart.domain.Cart;
import com.example.bookstore.cart.service.CartService;
import com.example.bookstore.inventory.domain.Inventory;
import com.example.bookstore.inventory.service.InventoryService;
import com.example.bookstore.order.domain.Order;
import com.example.bookstore.order.domain.OrderItem;
import com.example.bookstore.order.dto.OrderDto;
import com.example.bookstore.order.repository.OrderRepository;
import com.example.bookstore.user.domain.User;
import com.example.bookstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderItemService orderItemService;
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final InventoryService inventoryService;
    private final CartService cartService;

    public void save(Long inventoryId, int quantity, String email) {
        User user = userService.findUserByEmail(email);
        Inventory inventory = inventoryService.findInventoryById(inventoryId);

        if (!inventory.quantityIsMoreThan(quantity)) {
            throw new IllegalArgumentException("재고가 부족한 상품이 포함되어 주문을 진행할 수 없습니다.");
        }

        Order order = new Order(user);
        orderRepository.save(order);

        // OrderItem 저장
        OrderItem orderItem = new OrderItem(order, inventory, quantity);
        orderItemService.save(orderItem);

        // 재고 차감
        inventory.minusQuantity(quantity);
    }

    public void save(List<Long> cartIds, String email) {
        User user = userService.findUserByEmail(email);

        if (cartIds.isEmpty()) {
            throw new IllegalArgumentException("장바구니에 담긴 상품이 없습니다.");
        }

        // 모든 상품의 재고 확인
        for (Long id : cartIds) {
            Cart cart = cartService.findCartById(id);
            Inventory inventory = cart.getInventory();
            int quantity = cart.getQuantity();

            if (!inventory.quantityIsMoreThan(quantity)) {
                throw new IllegalArgumentException("재고가 부족한 상품이 포함되어 주문을 진행할 수 없습니다.");
            }
        }

        // 주문 생성
        Order order = new Order(user);
        orderRepository.save(order);

        // 주문 처리
        for (Long id : cartIds) {
            Cart cart = cartService.findCartById(id);
            Inventory inventory = cart.getInventory();
            int quantity = cart.getQuantity();

            // OrderItem 저장
            OrderItem orderItem = new OrderItem(order, inventory, quantity);
            orderItemService.save(orderItem);

            // 재고 차감
            inventory.minusQuantity(quantity);

            // 장바구니 삭제
            cartService.deleteById(cart.getCartId());
        }
    }

    public List<OrderDto> findByUser(String email) {
        User user = userService.findUserByEmail(email);
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream()
                .map(order -> OrderDto.from(order))
                .toList();
        return orderDtos;
    }

    public OrderDto findOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException());
        OrderDto orderDto = OrderDto.from(order);
        return orderDto;
    }

    // 주문 상태 변경, 이름이 어색한 것 같다
    public void update(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new NoSuchElementException());
        order.cancelOrder();

        // 재고 돌려놓기
        List<OrderItem> orderItems = orderItemService.findOrderItemByOrder(orderId);
        for (OrderItem orderItem : orderItems) {
            Inventory inventory = orderItem.getInventory();
            inventory.plusQuantity(orderItem.getQuantity());
        }
    }
}
