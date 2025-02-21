package com.example.bookstore.inventory.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Inventory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    private String title;
    private String contents;
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

    @Enumerated(EnumType.STRING)
    private InventoryStatus status;

    /**
     * 나중에 주문할 때 수정자, 수정시각 변할 수 있음
     * 수량을 줄이는 더티체킹 때문에
     */
    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    /**
     * 누가 했는지 기록하려면 AuditorAware 필요
     */
    @CreatedBy
    private String createdBy;
    @LastModifiedBy
    private String lastModifiedBy;

    public void plusQuantity(int quantity) {
        this.quantity += quantity;
    }

    public void minusQuantity(int quantity) {
        this.quantity -= quantity;
        if (this.quantity == 0) {
            this.status = InventoryStatus.OUT_OF_STOCK;
        }
    }

    public boolean quantityIsMoreThan(int quantity) {
        return this.quantity >= quantity;
    }

    public void changeQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void changeStatus(String status) {
        this.status = InventoryStatus.valueOf(status);
    }

    public int totalPrice(int quantity) {
        return this.salePrice * quantity;
    }
}
