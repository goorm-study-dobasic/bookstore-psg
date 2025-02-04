package com.example.bookstore.deliveryaddress.domain;

import com.example.bookstore.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Getter
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
}
