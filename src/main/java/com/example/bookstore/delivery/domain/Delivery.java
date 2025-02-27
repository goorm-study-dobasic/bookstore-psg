package com.example.bookstore.delivery.domain;

import com.example.bookstore.deliveryaddress.domain.DeliveryAddressInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Delivery {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryAddressInfo deliveryAddressInfo;

    private LocalDateTime startedAt;
    private LocalDateTime arrivedAt;

    public Delivery(DeliveryAddressInfo deliveryAddressInfo) {
        this.deliveryAddressInfo = deliveryAddressInfo;
    }

    // 출도착시간은 나중에
}
