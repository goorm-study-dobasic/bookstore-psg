package com.example.bookstore.order.controller;

import com.example.bookstore.inventory.dto.OrderItemDto;
import com.example.bookstore.order.dto.OrderDto;
import com.example.bookstore.order.service.OrderItemService;
import com.example.bookstore.order.service.OrderService;
import com.example.bookstore.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderItemService orderItemService;

    @GetMapping
    public String getOrders(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("orderDtos", orderService.findByUser(customUserDetails.getUsername()));

        return "user/order";
    }

    @PostMapping(params = {"cartIds"})
    public String createOrder(@RequestParam List<Long> cartIds,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails,
                              RedirectAttributes redirectAttributes) {
        try {
            orderService.save(cartIds, customUserDetails.getUsername());
            return "redirect:/users/orders";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/carts"; // 장바구니 페이지로 리다이렉트
        }
    }

    @PostMapping(params = {"inventoryId", "quantity"})
    public String createOrder(@RequestParam("inventoryId") Long inventoryId,
                              @RequestParam("quantity") int quantity,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails,
                              RedirectAttributes redirectAttributes) {
        try {
            orderService.save(inventoryId, quantity, customUserDetails.getUsername());
            return "redirect:/users/orders";
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/users/carts"; // 장바구니 페이지로 리다이렉트
        }
    }

    @PostMapping("/detail")
    public String getOrderDetail(@RequestParam("orderId") Long orderId, Model model) {
        List<OrderItemDto> orderItemDtos = orderItemService.findOrderItemDtoByOrder(orderId);
        OrderDto orderDto = orderService.findOrderById(orderId);

        model.addAttribute("orderDto", orderDto);
        model.addAttribute("orderItemDtos", orderItemDtos);
        return "user/order/detail";
    }

    @PostMapping("/cancel")
    public String cancelOrder(@RequestParam("orderId") Long orderId) {
        orderService.update(orderId);

        return "redirect:/users/orders";
    }
}
