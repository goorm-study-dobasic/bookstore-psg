package com.example.bookstore.deliveryaddress.service;

import com.example.bookstore.deliveryaddress.domain.DeliveryAddressInfo;
import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoCreateDto;
import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoUpdateDto;
import com.example.bookstore.deliveryaddress.repository.DeliveryAddressInfoRepository;
import com.example.bookstore.user.domain.User;
import com.example.bookstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryAddressInfoService {

    private final DeliveryAddressInfoRepository deliveryAddressInfoRepository;
    private final UserService userService;

    public List<DeliveryAddressInfoDto> findByUser(String email) {
        User user = userService.findUserByEmail(email);


        List<DeliveryAddressInfo> deliveryAddressInfos = deliveryAddressInfoRepository.findByUser(user);

        return deliveryAddressInfos
                .stream()
                .map(DeliveryAddressInfoDto::from)
                .toList();
    }

    public void save(User savedUser, String zipcode, String streetAddr, String detailAddr) {

        DeliveryAddressInfo deliveryAddressInfo = DeliveryAddressInfo.builder()
                .user(savedUser)
                .addressName("기본 배송지")
                .zipcode(zipcode)
                .streetAddr(streetAddr)
                .detailAddr(detailAddr)
                .etc(null)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
        deliveryAddressInfoRepository.save(deliveryAddressInfo);
    }

    public void save(String email, DeliveryAddressInfoCreateDto deliveryAddressInfoCreateDto) {
        User user = userService.findUserByEmail(email);
        DeliveryAddressInfo deliveryAddressInfo = DeliveryAddressInfo
                .builder()
                .user(user)
                .addressName(deliveryAddressInfoCreateDto.getAddressName())
                .zipcode(deliveryAddressInfoCreateDto.getZipcode())
                .streetAddr(deliveryAddressInfoCreateDto.getStreetAddr())
                .detailAddr(deliveryAddressInfoCreateDto.getDetailAddr())
                .etc(deliveryAddressInfoCreateDto.getEtc())
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();

        deliveryAddressInfoRepository.save(deliveryAddressInfo);
    }

    public void update(DeliveryAddressInfoUpdateDto deliveryAddressInfoUpdateDto) {
        DeliveryAddressInfo deliveryAddressInfo = deliveryAddressInfoRepository.findById(deliveryAddressInfoUpdateDto.getDeliveryAddressInfoSeq())
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 배송지 주소입니다."));

        deliveryAddressInfo.changeDeliveryAddressInfo(deliveryAddressInfoUpdateDto.getAddressName(), deliveryAddressInfoUpdateDto.getZipcode(), deliveryAddressInfoUpdateDto.getStreetAddr(), deliveryAddressInfoUpdateDto.getDetailAddr(), deliveryAddressInfoUpdateDto.getEtc());
    }

    public void delete(Long deliveryAddressInfoSeq) {
        deliveryAddressInfoRepository.deleteById(deliveryAddressInfoSeq);
    }

    public boolean checkDuplicateAddressName(String email, String addressName) {
        User user = userService.findUserByEmail(email);
        List<DeliveryAddressInfo> deliveryAddressInfos = deliveryAddressInfoRepository.findByUser(user);

        return deliveryAddressInfos
                .stream()
                .anyMatch(deliveryAddressInfo -> deliveryAddressInfo.getAddressName().equals(addressName));
    }

    public DeliveryAddressInfoDto findBySeq(Long deliveryAddressSeq) {
        DeliveryAddressInfo deliveryAddressInfo = deliveryAddressInfoRepository.findById(deliveryAddressSeq)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 배송지 주소입니다."));

        return DeliveryAddressInfoDto.from(deliveryAddressInfo);
    }

    public DeliveryAddressInfo findDeliveryAddressInfoBySeq(Long deliveryAddressSeq) {
        DeliveryAddressInfo deliveryAddressInfo = deliveryAddressInfoRepository.findById(deliveryAddressSeq)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 배송지 주소입니다."));

        return deliveryAddressInfo;
    }
}
