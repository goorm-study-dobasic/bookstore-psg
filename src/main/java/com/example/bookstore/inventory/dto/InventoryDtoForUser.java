package com.example.bookstore.inventory.dto;

import com.example.bookstore.inventory.domain.Inventory;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class InventoryDtoForUser {

    private Long inventoryId;
    private String title;
    private String isbn;
    private String authors;
    private String publisher;
    private int salePrice;
    private String thumbnail;

    public static InventoryDtoForUser from(Inventory inventory) {
        return InventoryDtoForUser.builder()
                .inventoryId(inventory.getInventoryId())
                .title(inventory.getTitle())
                .isbn(inventory.getIsbn())
                .authors(inventory.getAuthors())
                .publisher(inventory.getPublisher())
                .salePrice(inventory.getSalePrice())
                .thumbnail(inventory.getThumbnail())
                .build();
    }
}
