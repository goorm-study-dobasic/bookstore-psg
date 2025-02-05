package com.example.bookstore.blacklist.dto;

import com.example.bookstore.blacklist.domain.Blacklist;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class BlacklistDto {

    private String email;
    private String reason;

    private LocalDateTime blacklistedAt;
    private LocalDateTime unleashedAt;
    private String blacklistedBy;
    private String unleashedBy;

    public static BlacklistDto from(Blacklist blacklist) {
        return BlacklistDto.builder()
                .email(blacklist.getUser().getEmail()) // N + 1 발생 가능
                .reason(blacklist.getReason())
                .blacklistedAt(blacklist.getBlacklistedAt())
                .unleashedAt(blacklist.getUnleashedAt())
                .blacklistedBy(blacklist.getBlacklistedBy())
                .unleashedBy(blacklist.getUnleashedBy())
                .build();

    }
}
