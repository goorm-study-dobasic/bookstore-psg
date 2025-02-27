package com.example.bookstore.delivery.controller;

import com.example.bookstore.delivery.dto.DeliveryDto;
import com.example.bookstore.delivery.service.DeliveryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @GetMapping("/delivery/{id}")
    public String getDelivery(@PathVariable("id") Long id, Model model) {
        DeliveryDto deliveryDto = deliveryService.getDeliveryDto(id);
        model.addAttribute("deliveryDto", deliveryDto);

        return "delivery/detail";
    }
}
