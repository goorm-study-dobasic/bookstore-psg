package com.example.bookstore.cart.dto;

import lombok.Data;

@Data
public class UpdateCartDto {

    private Long cartId;
    private int quantity;
}
