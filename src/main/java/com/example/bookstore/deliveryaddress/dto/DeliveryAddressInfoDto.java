package com.example.bookstore.deliveryaddress.dto;

import com.example.bookstore.deliveryaddress.domain.DeliveryAddressInfo;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DeliveryAddressInfoDto {
    private Long deliveryAddressSeq;
    private String addressName;
    private String zipcode;
    private String streetAddr;
    private String detailAddr;
    private String etc;

    public static DeliveryAddressInfoDto from(DeliveryAddressInfo deliveryAddressInfo) {
        return DeliveryAddressInfoDto
                .builder()
                .deliveryAddressSeq(deliveryAddressInfo.getDeliveryAddressInfoSeq())
                .addressName(deliveryAddressInfo.getAddressName())
                .zipcode(deliveryAddressInfo.getZipcode())
                .streetAddr(deliveryAddressInfo.getStreetAddr())
                .detailAddr(deliveryAddressInfo.getDetailAddr())
                .etc(deliveryAddressInfo.getEtc())
                .build();
    }
}
