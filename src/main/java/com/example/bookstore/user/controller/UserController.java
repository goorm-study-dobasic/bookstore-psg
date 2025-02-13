package com.example.bookstore.user.controller;

import com.example.bookstore.user.dto.CustomUserDetails;
import com.example.bookstore.user.dto.UpdateUserDto;
import com.example.bookstore.user.dto.UserDto;
import com.example.bookstore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    public String myPage(@AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        String email = customUserDetails.getUsername();
        UserDto userDto = userService.findUserDtoByEmail(email);

        model.addAttribute("userDto", userDto);
        return "user/detail";
    }

    @PostMapping("/users/edit")
    public String edit(@RequestParam("email") String email, Model model) {
        UserDto userDto = userService.findUserDtoByEmail(email);
        model.addAttribute("updateUserDto", UpdateUserDto.from(userDto));

        return "user/edit";
    }

    @PostMapping("/users")
    public String edit(@Valid @ModelAttribute("updateUserDto") UpdateUserDto updateUserDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user/edit";
        }
        userService.updateUser(updateUserDto);

        return "redirect:/users";
    }

    // 회원탈퇴
    @PostMapping("/users/delete")
    public String delete(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        userService.delete(customUserDetails.getUsername());

        return "redirect:/logout";
    }
}
