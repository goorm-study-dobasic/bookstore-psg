package com.example.bookstore.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInventoryDto {

    private Long inventoryId;
    private int quantity;
    private String status;

    public UpdateInventoryDto(int quantity, String status) {
        this.quantity = quantity;
        this.status = status;
    }
}
