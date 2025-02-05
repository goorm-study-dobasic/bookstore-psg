package com.example.bookstore.blacklist.domain;

import com.example.bookstore.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Blacklist {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long blacklistSeq;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_seq")
    private User user;

    private String reason;

    private LocalDateTime blacklistedAt;
    private LocalDateTime unleashedAt;
    private String blacklistedBy;
    private String unleashedBy;

    public void unleashed(String unleashedBy) {
        this.unleashedAt = LocalDateTime.now();
        this.unleashedBy = unleashedBy;
        user.changeUseY();
    }
}
