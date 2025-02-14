package com.example.bookstore.inventory.dto;

import com.example.bookstore.api.dto.KakaoBookDocument;
import com.example.bookstore.inventory.domain.InventoryStatus;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@ToString
public class AddInventoryDto {
    private String title;
    private String contents;
    private String url;
    private String isbn;

    private LocalDateTime datetime;
    private String[] authors;
    private String publisher;
    private String[] translators;
    private int price;
    private int salePrice;
    private String thumbnail;
    private InventoryStatus status;
    private int quantity;

    public static AddInventoryDto from(KakaoBookDocument kakaoBookDocument) {
        return AddInventoryDto.builder()
                .title(kakaoBookDocument.getTitle())
                .contents(kakaoBookDocument.getContents())
                .url(kakaoBookDocument.getUrl())
                .isbn(kakaoBookDocument.getIsbn())
                .datetime(kakaoBookDocument.getDatetime().toLocalDateTime())
                .authors(kakaoBookDocument.getAuthors())
                .publisher(kakaoBookDocument.getPublisher())
                .translators(kakaoBookDocument.getTranslators())
                .price(kakaoBookDocument.getPrice())
                .salePrice(kakaoBookDocument.getSalePrice())
                .thumbnail(kakaoBookDocument.getThumbnail())
                .status(InventoryStatus.ON_SALES)
                .build();
    }

}
