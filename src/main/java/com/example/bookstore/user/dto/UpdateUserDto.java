package com.example.bookstore.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserDto {
    private String email;

    @NotBlank(message = "전화번호를 입력해야 합니다.")
    @Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$", message = "전화번호 형식은 000-0000-0000이어야 합니다.")
    private String phone;

    @NotBlank(message = "닉네임을 입력해야 합니다.")
    @Size(min = 2, max = 10, message = "닉네임은 최소 2자리, 최대 10자리여야 합니다.")
    private String nickname;

    private String grade;
    private int mileage;

    public static UpdateUserDto from(UserDto userDto) {
        return UpdateUserDto.builder()
                .email(userDto.getEmail())
                .phone(userDto.getPhone())
                .nickname(userDto.getNickname())
                .grade(userDto.getGrade())
                .mileage(userDto.getMileage())
                .build();
    }
}
