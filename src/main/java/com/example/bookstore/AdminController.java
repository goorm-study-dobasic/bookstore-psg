package com.example.bookstore;

import com.example.bookstore.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    @GetMapping("/admin")
    public String admin() {

        return "admin/index";
    }

    @GetMapping("/admin/users")
    public String userList(Model model) {
        model.addAttribute("userDtos", userService.findAllUser());
        return "admin/userList";
    }

}
