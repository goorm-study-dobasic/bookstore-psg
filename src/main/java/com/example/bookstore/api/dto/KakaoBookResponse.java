package com.example.bookstore.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@Setter
@Getter
public class KakaoBookResponse {

    // Meta
    private KakaoMeta meta;

    // Document
    private List<KakaoBookDocument> documents;
}
