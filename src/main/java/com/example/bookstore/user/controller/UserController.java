package com.example.bookstore.user.controller;

import com.example.bookstore.user.dto.CustomUserDetails;
import com.example.bookstore.user.dto.UserDto;
import com.example.bookstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String myPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String email = customUserDetails.getUsername();
        UserDto userDto = userService.findByEmail(email);

        model.addAttribute("userDto", userDto);
        return "user/detail";
    }
}
