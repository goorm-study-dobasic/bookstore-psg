package com.example.bookstore.blacklist.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddBlacklistDto {

    private String email;

    @NotBlank(message = "사유를 입력해야 합니다.")
    private String reason;

    private String blacklistedBy;

    public AddBlacklistDto(String email, String blacklistedBy) {
        this.email = email;
        this.blacklistedBy = blacklistedBy;
    }
}
