package com.example.bookstore.user.dto;

import com.example.bookstore.user.domain.User;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserDto {
    private String email;
    private String phone;
    private String nickname;

    private String grade;
    private int mileage;

    private char useYn;

    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public static UserDto from(User user) {
        return UserDto
                .builder()
                .email(user.getEmail())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .grade(user.getGrade().name())
                .mileage(user.getMileage())
                .useYn(user.getUseYn())
                .createdAt(user.getCreatedAt())
                .lastModifiedAt(user.getLastModifiedAt())
                .build();
    }
}
