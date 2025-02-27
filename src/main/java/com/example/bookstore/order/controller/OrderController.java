package com.example.bookstore.order.controller;

import com.example.bookstore.cart.dto.CartDto;
import com.example.bookstore.cart.service.CartService;
import com.example.bookstore.deliveryaddress.dto.DeliveryAddressInfoDto;
import com.example.bookstore.deliveryaddress.service.DeliveryAddressInfoService;
import com.example.bookstore.inventory.dto.InventoryDtoForUser;
import com.example.bookstore.inventory.dto.OrderItemDto;
import com.example.bookstore.inventory.service.InventoryService;
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
    private final DeliveryAddressInfoService deliveryAddressInfoService;
    private final CartService cartService;
    private final InventoryService inventoryService;

    @GetMapping
    public String getOrders(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("orderDtos", orderService.findByUser(customUserDetails.getUsername()));

        return "user/order";
    }

    @PostMapping(value = "/create", params = {"cartIds"})
    public String createOrder(@RequestParam("selectedAddress") Long addressSeq,
                              @RequestParam("cartIds") List<Long> cartIds,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        orderService.save(cartIds, customUserDetails.getUsername(), addressSeq);

        return "redirect:/users/orders";
    }

    @PostMapping(value = "/create", params = {"inventoryId", "quantity"})
    public String createOrder(@RequestParam("inventoryId") Long inventoryId,
                              @RequestParam("quantity") int quantity,
                              @RequestParam("selectedAddress") Long addressSeq,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        orderService.save(inventoryId, quantity, customUserDetails.getUsername(), addressSeq);

        return "redirect:/users/orders";
    }

    @PostMapping(params = {"cartIds"})
    public String createOrderForm(@RequestParam List<Long> cartIds,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails,
                              Model model) {
        List<DeliveryAddressInfoDto> deliveryAddressInfoDtos = deliveryAddressInfoService.findByUser(customUserDetails.getUsername());
        List<CartDto> carts = cartService.findCartsByIds(cartIds);
        model.addAttribute("deliveryAddressInfoDtos", deliveryAddressInfoDtos);
        model.addAttribute("carts", carts);

        return "user/order/check";
    }

    @PostMapping(params = {"inventoryId", "quantity"})
    public String createOrderForm(@RequestParam("inventoryId") Long inventoryId,
                              @RequestParam("quantity") int quantity,
                              @AuthenticationPrincipal CustomUserDetails customUserDetails,
                              Model model,
                              RedirectAttributes redirectAttributes) {

        InventoryDtoForUser inventoryDtoForUser = inventoryService.findInventoryDtoForUserById(inventoryId);
        List<DeliveryAddressInfoDto> deliveryAddressInfoDtos = deliveryAddressInfoService.findByUser(customUserDetails.getUsername());
        model.addAttribute("deliveryAddressInfoDtos", deliveryAddressInfoDtos);
        model.addAttribute("quantity", quantity);
        model.addAttribute("inventoryDtoForUser", inventoryDtoForUser);

        return "user/order/check2";
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
