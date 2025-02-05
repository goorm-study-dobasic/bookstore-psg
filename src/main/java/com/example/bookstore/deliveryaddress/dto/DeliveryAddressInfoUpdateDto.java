package com.example.bookstore.deliveryaddress.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryAddressInfoUpdateDto {

    private Long deliveryAddressInfoSeq;

    @NotBlank(message = "별칭을 입력해야 합니다.")
    private String addressName;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String zipcode;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String streetAddr;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String detailAddr;
    private String etc;

    public static DeliveryAddressInfoUpdateDto from(DeliveryAddressInfoDto deliveryAddressInfoDto) {
        return DeliveryAddressInfoUpdateDto.builder()
                .deliveryAddressInfoSeq(deliveryAddressInfoDto.getDeliveryAddressSeq())
                .addressName(deliveryAddressInfoDto.getAddressName())
                .zipcode(deliveryAddressInfoDto.getZipcode())
                .streetAddr(deliveryAddressInfoDto.getStreetAddr())
                .detailAddr(deliveryAddressInfoDto.getDetailAddr())
                .etc(deliveryAddressInfoDto.getEtc())
                .build();
    }
}
