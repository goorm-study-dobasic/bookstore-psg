package com.example.bookstore.user.service;

import com.example.bookstore.deliveryaddress.domain.DeliveryAddressInfo;
import com.example.bookstore.deliveryaddress.repository.DeliveryAddressInfoRepository;
import com.example.bookstore.user.domain.User;
import com.example.bookstore.user.domain.UserGrade;
import com.example.bookstore.user.domain.UserRole;
import com.example.bookstore.user.dto.JoinUserDto;
import com.example.bookstore.user.dto.UserDto;
import com.example.bookstore.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeliveryAddressInfoRepository deliveryAddressInfoRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public boolean checkDuplicateEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void join(JoinUserDto joinUserDto) {
        User user = User.builder()
                .email(joinUserDto.getEmail())
                .password(bCryptPasswordEncoder.encode(joinUserDto.getPassword()))
                .phone(joinUserDto.getPhone())
                .nickname(joinUserDto.getNickname())
                .grade(UserGrade.BRONZE)
                .mileage(0)
                .useYn('Y')
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .role(UserRole.ROLE_USER)
                .build();

        User savedUser = userRepository.save(user);

        DeliveryAddressInfo deliveryAddressInfo = DeliveryAddressInfo.builder()
                .user(savedUser)
                .addressName("기본 배송지")
                .zipcode(joinUserDto.getZipcode())
                .streetAddr(joinUserDto.getStreetAddr())
                .detailAddr(joinUserDto.getDetailAddr())
                .etc("없음")
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        deliveryAddressInfoRepository.save(deliveryAddressInfo);
    }

    public UserDto findByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("유저가 존재하지 않습니다."));

        UserDto userDto = UserDto.from(user);
        return userDto;
    }
}
