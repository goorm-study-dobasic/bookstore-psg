package com.example.bookstore.blacklist.controller;

import com.example.bookstore.blacklist.dto.AddBlacklistDto;
import com.example.bookstore.blacklist.service.BlacklistService;
import com.example.bookstore.user.dto.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistService blacklistService;

    @GetMapping("/blacklist")
    public String blacklist(Model model) {
        model.addAttribute("blacklistDtos", blacklistService.findAll());

        return "admin/blacklist";
    }

    @PostMapping("/blacklist/add")
    public String add(@RequestParam("email") String email, @AuthenticationPrincipal CustomUserDetails customUserDetails, Model model) {
        model.addAttribute("addBlacklistDto", new AddBlacklistDto(email, customUserDetails.getNickname()));

        return "admin/blacklist/add";
    }

    @PostMapping("/blacklist")
    public String add(@Valid AddBlacklistDto addBlacklistDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/blacklist/add";
        }

        blacklistService.save(addBlacklistDto);
        return "redirect:/admin/blacklist";
    }

    @PostMapping("/blacklist/delete")
    public String delete(@RequestParam("email") String email, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        blacklistService.unleashed(email, customUserDetails.getNickname());

        return "redirect:/admin/blacklist";
    }
}
