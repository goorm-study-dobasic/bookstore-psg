package com.example.bookstore.blacklist.service;

import com.example.bookstore.blacklist.domain.Blacklist;
import com.example.bookstore.blacklist.dto.AddBlacklistDto;
import com.example.bookstore.blacklist.dto.BlacklistDto;
import com.example.bookstore.blacklist.repository.BlacklistRepository;
import com.example.bookstore.user.domain.User;
import com.example.bookstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;
    private final UserRepository userRepository;

    public void save(AddBlacklistDto addBlacklistDto) {
        User user = userRepository.findByEmail(addBlacklistDto.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));

        Blacklist blacklist = Blacklist.builder()
                .user(user)
                .reason(addBlacklistDto.getReason())
                .blacklistedAt(LocalDateTime.now())
                .unleashedAt(null)
                .blacklistedBy(addBlacklistDto.getBlacklistedBy())
                .unleashedBy(null)
                .build();

        user.changeUseN();
        blacklistRepository.save(blacklist);
    }

    public void unleashed(String email, String unleashedBy) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));

        Blacklist blacklist = blacklistRepository.findByUserAndUnleashedAtIsNull(user);
        blacklist.unleashed(unleashedBy);
    }

    // 해제되지 않은 블랙리스트 목록
    public List<BlacklistDto> findAll() {
        List<Blacklist> allByUnleashedAtIsNotNull = blacklistRepository.findAllByUnleashedAtIsNotNull();
        List<Blacklist> all = blacklistRepository.findAll();

//        return allByUnleashedAtIsNotNull
        return all
                .stream()
                .map(blacklist -> BlacklistDto.from(blacklist))
                .toList();
    }
}
