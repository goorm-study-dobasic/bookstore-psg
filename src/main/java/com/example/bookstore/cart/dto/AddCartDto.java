package com.example.bookstore.cart.dto;

import lombok.Data;

@Data
public class AddCartDto {

    // 엔티티가 Dto에 담기면 안됨
    private String email;
    private Long inventoryId;
    private int quantity;

}
