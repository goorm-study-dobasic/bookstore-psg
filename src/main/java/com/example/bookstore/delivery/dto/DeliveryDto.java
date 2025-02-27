package com.example.bookstore.delivery.dto;

import com.example.bookstore.delivery.domain.Delivery;
import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class DeliveryDto {

    private Long id;
    private DeliveryAddressInfoDto deliveryAddressInfoDto;
    private LocalDateTime startedAt;
    private LocalDateTime arrivedAt;

    public static DeliveryDto from(Delivery delivery) {
        return DeliveryDto.builder()
                .id(delivery.getId())
                .deliveryAddressInfoDto(DeliveryAddressInfoDto.from(delivery.getDeliveryAddressInfo()))
                .startedAt(delivery.getStartedAt())
                .arrivedAt(delivery.getArrivedAt())
                .build();
    }

}
