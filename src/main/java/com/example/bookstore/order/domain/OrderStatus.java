package com.example.bookstore.order.domain;

public enum OrderStatus {
    CANCEL("주문취소"), PENDING("결제대기"), PAID("결제완료"), PREPARING("배송준비"), SHIPPING("배송중"), DELIVERED("배송완료");

    private final String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
