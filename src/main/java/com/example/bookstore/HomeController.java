package com.example.bookstore;

import com.example.bookstore.cart.dto.AddCartDto;
import com.example.bookstore.deliveryaddress.service.DeliveryAddressInfoService;
import com.example.bookstore.inventory.dto.InventoryDtoForUser;
import com.example.bookstore.inventory.service.InventoryService;
import com.example.bookstore.user.domain.User;
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
    private final DeliveryAddressInfoService deliveryAddressInfoService;
    private final InventoryService inventoryService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("books", inventoryService.findAllForUser());
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

        User savedUser = userService.save(joinUserDto);
        deliveryAddressInfoService.save(savedUser, joinUserDto.getZipcode(), joinUserDto.getStreetAddr(), joinUserDto.getDetailAddr());
        return "redirect:/login";
    }

    @GetMapping("/join/duplicate/email")
    @ResponseBody
    public Map<String, Boolean> checkDuplicateEmail(@RequestParam("email") String email) {
        System.out.println("이메일 중복 확인 요청: " + email); // 디버깅 로그
        boolean exists = userService.checkDuplicateEmail(email);
        return Collections.singletonMap("exists", exists);
    }

    @PostMapping("/books/product")
    public String getBook(@RequestParam("inventoryId") Long inventoryId,
                          @RequestParam(value = "quantity", required = false) Integer quantity,
                          Model model) {
        InventoryDtoForUser inventoryDtoForUser = inventoryService.findInventoryDtoForUserById(inventoryId);

        model.addAttribute("addCartDto", new AddCartDto());
        model.addAttribute("inventoryDto", inventoryDtoForUser);

        // 사용자가 입력한 quantity가 존재하고, 재고보다 많으면 에러 메시지 추가
        if (quantity != null && quantity > inventoryDtoForUser.getQuantity()) {
            model.addAttribute("errorMessage", "남은 재고보다 많이 구매할 수 없습니다.");
        }

        return "inventory/product";
    }

}
