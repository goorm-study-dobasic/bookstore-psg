package com.example.bookstore.deliveryaddress.controller;

import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import com.example.bookstore.deliveryaddress.service.DeliveryAddressInfoService;
import com.example.bookstore.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @PostMapping("/deliveryaddressinfo/edit")
    public String edit(@RequestParam("deliveryAddressSeq") Long deliveryAddressSeq, Model model) {
        System.out.println("id = " + deliveryAddressSeq);
        return "deliveryaddressinfo/edit";
    }

    @PostMapping("/deliveryaddressinfo/delete")
    public String delete(@RequestParam("deliveryAddressSeq") Long deliveryAddressSeq) {
        System.out.println("id = " + deliveryAddressSeq);
        return "redirect:/users/deliveryaddressinfo";
    }

}
