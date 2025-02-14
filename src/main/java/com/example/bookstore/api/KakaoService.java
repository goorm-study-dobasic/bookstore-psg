package com.example.bookstore.api;

import com.example.bookstore.api.dto.KakaoBookDocument;
import com.example.bookstore.api.dto.KakaoBookResponse;
import com.example.bookstore.api.dto.KakaoMeta;
import com.example.bookstore.inventory.dto.AddInventoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private static final String REQUEST_URI_STRING = "https://dapi.kakao.com";
    private static final String REQUEST_PATH = "/v3/search/book";
    private static final int REQUEST_QUERY_SIZE = 10;

    private final RestTemplate restTemplate;

    @Value("${kakao.api.key}")
    private String apiKey;

    public Page<KakaoBookDocument> searchBooks(String query, String target, int page) {
        if (query == null || query.isBlank()) {
            return Page.empty();
        }

        URI uri = UriComponentsBuilder
                .fromUriString(REQUEST_URI_STRING)
                .path(REQUEST_PATH)
                .queryParam("query", query)
                .queryParam("page", page)
                .queryParam("size", REQUEST_QUERY_SIZE)
                .queryParamIfPresent("target", Optional.ofNullable(target))
                .encode()
                .build()
                .toUri();

        System.out.println(uri);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<KakaoBookResponse> result = restTemplate
                .exchange(uri, HttpMethod.GET, httpEntity, KakaoBookResponse.class);

        List<KakaoBookDocument> documents = result.getBody().getDocuments();
        KakaoMeta meta = result.getBody().getMeta();

        return new PageImpl<>(documents, PageRequest.of(page - 1, REQUEST_QUERY_SIZE), meta.getTotalCount());
    }

    public AddInventoryDto searchBook(String query) {
        String[] isbn = query.trim().split(" ");
        String firstIsbn = isbn[0];

        System.out.println(Arrays.toString(isbn));

        URI uri = UriComponentsBuilder
                .fromUriString(REQUEST_URI_STRING)
                .path(REQUEST_PATH)
                .queryParam("query", firstIsbn)
                .queryParam("target", "isbn")
                .encode()
                .build()
                .toUri();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        HttpEntity<Object> httpEntity = new HttpEntity<>(headers);

        ResponseEntity<KakaoBookResponse> result = restTemplate
                .exchange(uri, HttpMethod.GET, httpEntity, KakaoBookResponse.class);

        KakaoBookDocument kakaoBookDocument = result.getBody().getDocuments().get(0);
        return AddInventoryDto.from(kakaoBookDocument);
    }

}