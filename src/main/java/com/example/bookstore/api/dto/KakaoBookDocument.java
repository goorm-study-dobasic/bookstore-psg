package com.example.bookstore.api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
public class KakaoBookDocument {
    private String title;
    private String contents;
    private String url;
    private String isbn;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "Asia/Seoul")
    private OffsetDateTime datetime;
    private String[] authors;
    private String publisher;
    private String[] translators;
    private int price;

    @JsonProperty("sale_price")
    private int salePrice;

    private String thumbnail;
    private String status;

}
