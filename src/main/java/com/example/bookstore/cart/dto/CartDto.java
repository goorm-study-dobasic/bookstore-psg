package com.example.bookstore.cart.dto;

import com.example.bookstore.cart.domain.Cart;
import com.example.bookstore.inventory.dto.InventoryDtoForUser;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartDto {

    private Long cartId;
    private InventoryDtoForUser inventoryDtoForUser;
    private int quantity;

    public static CartDto from(Cart cart) {
        return CartDto.builder()
                .cartId(cart.getCartId())
                .inventoryDtoForUser(InventoryDtoForUser.from(cart.getInventory()))
                .quantity(cart.getQuantity())
                .build();
    }
}
