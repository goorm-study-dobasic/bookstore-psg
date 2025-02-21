package com.example.bookstore.cart.service;

import com.example.bookstore.cart.domain.Cart;
import com.example.bookstore.cart.dto.AddCartDto;
import com.example.bookstore.cart.dto.CartDto;
import com.example.bookstore.cart.dto.UpdateCartDto;
import com.example.bookstore.cart.repository.CartRepository;
import com.example.bookstore.inventory.domain.Inventory;
import com.example.bookstore.inventory.service.InventoryService;
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
public class CartService {

    private final CartRepository cartRepository;
    private final UserService userService;
    private final InventoryService inventoryService; // inventoryService에 findById 메서드 만듬

    public void save(AddCartDto addCartDto) {
        User user = userService.findUserByEmail(addCartDto.getEmail());
        Inventory inventory = inventoryService.findInventoryById(addCartDto.getInventoryId());

        if (cartRepository.existsByUserAndInventory(user, inventory)) {
            Cart cart = cartRepository.findByUserAndInventory(user, inventory); // cartRepository에 findByUserAndInventory 만듬
            cart.addQuantity(addCartDto.getQuantity());
            return;
        }

        Cart cart = new Cart(user, inventory, addCartDto.getQuantity());
        cartRepository.save(cart);
    }

    public Cart findCartById(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new NoSuchElementException());
        return cart;
    }

    public List<CartDto> findAllByUser(String email) {
        User user = userService.findUserByEmail(email);
        List<Cart> all = cartRepository.findByUser(user);
        return all.stream()
                .map(cart -> CartDto.from(cart))
                .toList();
    }

    public void updateById(UpdateCartDto updateCartDto) {
        Cart cart = cartRepository.findById(updateCartDto.getCartId())
                .orElseThrow(() -> new NoSuchElementException());

        cart.changeQuantity(updateCartDto.getQuantity());
    }

    public void deleteById(Long cartId) {
        cartRepository.deleteById(cartId);
    }

}
