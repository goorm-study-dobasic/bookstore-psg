package com.example.bookstore.cart.controller;

import com.example.bookstore.cart.dto.AddCartDto;
import com.example.bookstore.cart.dto.CartDto;
import com.example.bookstore.cart.dto.UpdateCartDto;
import com.example.bookstore.cart.service.CartService;
import com.example.bookstore.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/carts")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCarts(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        List<CartDto> cartDtos = cartService.findAllByUser(customUserDetails.getUsername());
        model.addAttribute("cartDtos", cartDtos);

        return "user/cart";
    }

    @PostMapping("/add")
    public String addCart(@ModelAttribute AddCartDto addCartDto, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        addCartDto.setEmail(customUserDetails.getUsername());
        cartService.save(addCartDto);

        return "redirect:/users/carts";
    }

    @PostMapping("/edit")
    public String updateCart(@ModelAttribute UpdateCartDto updateCartDto) {
        cartService.updateById(updateCartDto);

        return "redirect:/users/carts";
    }

    @PostMapping("/delete")
    public String deleteCart(@RequestParam("cartId") Long cartId) {
        cartService.deleteById(cartId);

        return "redirect:/users/carts";
    }
}
