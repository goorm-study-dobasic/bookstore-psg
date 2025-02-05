package com.example.bookstore.deliveryaddress.controller;

import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoCreateDto;
import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoUpdateDto;
import com.example.bookstore.deliveryaddress.service.DeliveryAddressInfoService;
import com.example.bookstore.user.dto.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class DeliveryAddressInfoController {

    private final DeliveryAddressInfoService deliveryAddressInfoService;

    @GetMapping("/deliveryaddressinfo")
    public String deliveryAddressInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {

        model.addAttribute("deliveryAddressInfoDtos", deliveryAddressInfoService.findByUser(customUserDetails.getUsername()));

        return "deliveryaddressinfo/list";
    }

    @GetMapping("/deliveryaddressinfo/create")
    public String create(Model model) {
        model.addAttribute("deliveryAddressInfoCreateDto", new DeliveryAddressInfoCreateDto());

        return "deliveryaddressinfo/create";
    }

    @GetMapping("/join/duplicate/alias")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateAddressName(@RequestParam("addressName") String addressName, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println(customUserDetails.getUsername() + "의 별칭 중복 확인 요청: " + addressName); // 디버깅 로그
        boolean exists = deliveryAddressInfoService.checkDuplicateAddressName(customUserDetails.getUsername(), addressName);

        return Collections.singletonMap("exists", exists);
    }

    @PostMapping("/deliveryaddressinfo/create")
    public String create(@AuthenticationPrincipal CustomUserDetails customUserDetails, @Valid DeliveryAddressInfoCreateDto deliveryAddressInfoCreateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "deliveryaddressinfo/create";
        }

        deliveryAddressInfoService.save(customUserDetails.getUsername(), deliveryAddressInfoCreateDto);
        return "redirect:/users/deliveryaddressinfo";
    }

    // 수정폼 조회용
    @PostMapping("/deliveryaddressinfo/edit")
    public String edit(@RequestParam("deliveryAddressSeq") Long deliveryAddressSeq, Model model) {
        DeliveryAddressInfoDto deliveryAddressInfoDto = deliveryAddressInfoService.findBySeq(deliveryAddressSeq);
        model.addAttribute("deliveryAddressInfoUpdateDto", DeliveryAddressInfoUpdateDto.from(deliveryAddressInfoDto));

        return "deliveryaddressinfo/edit";
    }

    // 수정
    @PostMapping("/deliveryaddressinfo")
    public String edit(@Valid DeliveryAddressInfoUpdateDto deliveryAddressInfoUpdateDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "deliveraddressinfo/edit";
        }

        deliveryAddressInfoService.update(deliveryAddressInfoUpdateDto);
        return "redirect:/users/deliveryaddressinfo";
    }

    @PostMapping("/deliveryaddressinfo/delete")
    public String delete(@RequestParam("deliveryAddressSeq") Long deliveryAddressSeq) {
        deliveryAddressInfoService.delete(deliveryAddressSeq);
        return "redirect:/users/deliveryaddressinfo";
    }

}
