package com.example.bookstore.inventory.dto;

import com.example.bookstore.inventory.domain.Inventory;
import com.example.bookstore.inventory.domain.InventoryStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class InventoryDtoForAdmin {

    private Long inventoryId;
    private String title;
    private String url;
    private String isbn;
    private LocalDateTime datetime;
    private String authors;
    private String publisher;
    private String translators;
    private int price;
    private int salePrice;
    private String thumbnail;
    private int quantity;
    private InventoryStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String createdBy;
    private String lastModifiedBy;

    public static InventoryDtoForAdmin from(Inventory inventory) {
        return InventoryDtoForAdmin.builder()
                .inventoryId(inventory.getInventoryId())
                .title(inventory.getTitle())
                .url(inventory.getUrl())
                .isbn(inventory.getIsbn())
                .datetime(inventory.getDatetime())
                .authors(inventory.getAuthors())
                .publisher(inventory.getPublisher())
                .translators(inventory.getTranslators())
                .price(inventory.getPrice())
                .salePrice(inventory.getSalePrice())
                .thumbnail(inventory.getThumbnail())
                .quantity(inventory.getQuantity())
                .status(inventory.getStatus())
                .createdAt(inventory.getCreatedAt())
                .lastModifiedAt(inventory.getLastModifiedAt())
                .createdBy(inventory.getCreatedBy())
                .lastModifiedBy(inventory.getLastModifiedBy())
                .build();
    }
}
