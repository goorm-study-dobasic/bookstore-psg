package com.example.bookstore.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class User {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSeq;

    @Column(unique = true)
    private String email;
    private String password;
    private String phone;
    private String nickname;

    @Enumerated(EnumType.STRING)
    private UserGrade grade;

    private int mileage;
    private char useYn;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    public void changeUserInfo(String phone, String nickname) {
        this.phone = phone;
        this.nickname = nickname;
        this.lastModifiedAt = LocalDateTime.now();
    }

    public void changeUseN() {
        this.useYn = 'N';
    }

    public void changeUseY() {
        this.useYn = 'Y';
    }
}
