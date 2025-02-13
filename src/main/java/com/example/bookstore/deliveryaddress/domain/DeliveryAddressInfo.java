package com.example.bookstore.deliveryaddress.domain;

import com.example.bookstore.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class DeliveryAddressInfo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long deliveryAddressInfoSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private String addressName;

    private String zipcode;

    @Column(name = "road_address")
    private String streetAddr;

    @Column(name = "address_detail")
    private String detailAddr;

    private String etc;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public void changeDeliveryAddressInfo(String addressName, String zipcode, String streetAddr, String detailAddr, String etc) {
        this.addressName = addressName;
        this.zipcode = zipcode;
        this.streetAddr = streetAddr;
        this.detailAddr = detailAddr;
        this.etc = etc;
        this.lastModifiedAt = LocalDateTime.now();
    }
}
