package com.example.bookstore.deliveryaddress.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryAddressInfoCreateDto {

    @NotBlank(message = "별칭을 입력해야 합니다.")
    private String addressName;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String zipcode;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String streetAddr;
    @NotBlank(message = "주소를 입력해야 합니다.")
    private String detailAddr;
    private String etc;

}
