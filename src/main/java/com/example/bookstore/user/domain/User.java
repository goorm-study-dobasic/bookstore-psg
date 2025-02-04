package com.example.bookstore.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
}
