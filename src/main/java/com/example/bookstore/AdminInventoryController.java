package com.example.bookstore;

import com.example.bookstore.api.KakaoService;
import com.example.bookstore.api.dto.KakaoBookDocument;
import com.example.bookstore.inventory.dto.AddInventoryDto;
import com.example.bookstore.inventory.dto.InventoryDtoForAdmin;
import com.example.bookstore.inventory.dto.UpdateInventoryDto;
import com.example.bookstore.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/inventory")
public class AdminInventoryController {

    private final KakaoService kakaoService;
    private final InventoryService inventoryService;

    @GetMapping
    public String inventories(Model model) {

        List<InventoryDtoForAdmin> inventoryDtoForAdmins = inventoryService.findAllForAdmin();
        model.addAttribute("books", inventoryDtoForAdmins);

        return "admin/inventory";
    }

    @GetMapping("/{isbn}")
    public String inventory(@PathVariable("isbn") String isbn, Model model) {
        InventoryDtoForAdmin inventoryDto = inventoryService.getInventoryByIsbn(isbn);

        model.addAttribute("inventoryDto", inventoryDto);
        return "inventory/detail";
    }

    @GetMapping("/edit/{inventoryId}")
    public String editForm(@PathVariable Long inventoryId, Model model) {
        InventoryDtoForAdmin inventoryDto = inventoryService.getInventoryById(inventoryId);
        model.addAttribute("inventoryDto", inventoryDto);

        UpdateInventoryDto updateInventoryDto = new UpdateInventoryDto(inventoryDto.getQuantity(), inventoryDto.getStatus().name());
        model.addAttribute("updateInventoryDto", updateInventoryDto);
        return "inventory/editForm";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute UpdateInventoryDto updateInventoryDto) {

        inventoryService.update(updateInventoryDto);
        return "redirect:/admin/inventory";
    }

    @GetMapping("/search")
    public String searchForm(@RequestParam(name = "query", required = false) String query,
                             @RequestParam(name = "target", required = false) String target,
                             @RequestParam(name = "page", defaultValue = "1") int page,
                             Model model) {

        Page<KakaoBookDocument> kakaoBookDocuments = kakaoService.searchBooks(query, target, page);

        model.addAttribute("books", kakaoBookDocuments.getContent());
        model.addAttribute("currentPage", kakaoBookDocuments.getNumber() + 1);
        model.addAttribute("totalPages", kakaoBookDocuments.getTotalPages());
        model.addAttribute("query", query);
        return "inventory/search";
    }

    @GetMapping("/add")
    public String addForm(@RequestParam(name = "query") String query,
                          Model model) {
        System.out.println(query);
        AddInventoryDto addInventoryDto = kakaoService.searchBook(query);

        model.addAttribute("inventoryDto", addInventoryDto);

        return "inventory/addForm";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute AddInventoryDto addInventoryDto) {
        if (inventoryService.isExists(addInventoryDto)) {
            inventoryService.plusQuantity(addInventoryDto);
            return "redirect:/admin/inventory";
        }

        inventoryService.save(addInventoryDto);
        return "redirect:/admin/inventory";
    }

}
