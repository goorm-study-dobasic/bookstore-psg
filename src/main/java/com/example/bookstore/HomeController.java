package com.example.bookstore;

import com.example.bookstore.user.dto.JoinUserDto;
import com.example.bookstore.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/join")
    public String join(Model model) {
        model.addAttribute("joinUserDto", new JoinUserDto());
        return "join";
    }

    @PostMapping("/join")
    public String join(@Valid JoinUserDto joinUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "join"; // 유효성 검증 실패 시 다시 회원가입 페이지로 이동
        }

        userService.save(joinUserDto);
        return "redirect:/login";
    }

    @GetMapping("/join/duplicate/email")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateEmail(@RequestParam("email") String email) {
        System.out.println("이메일 중복 확인 요청: " + email); // 디버깅 로그
        boolean exists = userService.checkDuplicateEmail(email);
        return Collections.singletonMap("exists", exists);
    }
}
