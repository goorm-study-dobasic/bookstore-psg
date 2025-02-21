package com.example.bookstore.inventory.service;

import com.example.bookstore.inventory.domain.Inventory;
import com.example.bookstore.inventory.dto.AddInventoryDto;
import com.example.bookstore.inventory.dto.InventoryDtoForAdmin;
import com.example.bookstore.inventory.dto.InventoryDtoForUser;
import com.example.bookstore.inventory.dto.UpdateInventoryDto;
import com.example.bookstore.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryDtoForAdmin save(AddInventoryDto addInventoryDto) {
        Inventory inventory = Inventory.builder()
                .title(addInventoryDto.getTitle())
                .contents(addInventoryDto.getContents())
                .url(addInventoryDto.getUrl())
                .isbn(addInventoryDto.getIsbn())
                .datetime(addInventoryDto.getDatetime())
                .authors(String.join(",", addInventoryDto.getAuthors()))
                .publisher(addInventoryDto.getPublisher())
                .translators(String.join(",", addInventoryDto.getTranslators()))
                .price(addInventoryDto.getPrice())
                .salePrice(addInventoryDto.getSalePrice() == -1 ? addInventoryDto.getPrice() : addInventoryDto.getSalePrice()) // 판매가격이 -1이면 정가로 등록
                .thumbnail(addInventoryDto.getThumbnail())
                .status(addInventoryDto.getStatus())
                .quantity(addInventoryDto.getQuantity())
                .build();
        inventoryRepository.save(inventory);

        return InventoryDtoForAdmin.from(inventory);
    }

    public Inventory findInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findInventoryByInventoryId(inventoryId)
                .orElseThrow(() -> new NoSuchElementException());

        return inventory;
    }

    public InventoryDtoForUser findInventoryDtoForUserById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findInventoryByInventoryId(inventoryId)
                .orElseThrow(() -> new NoSuchElementException());

        return InventoryDtoForUser.from(inventory);
    }

    public InventoryDtoForAdmin getInventoryByIsbn(String isbn) {
        Inventory inventory = inventoryRepository.findInventoryByIsbn(isbn);
        return InventoryDtoForAdmin.from(inventory);
    }

    public InventoryDtoForAdmin getInventoryById(Long inventoryId) {
        Inventory inventory = inventoryRepository.findInventoryByInventoryId(inventoryId)
                .orElseThrow(() -> new NoSuchElementException());
        return InventoryDtoForAdmin.from(inventory);
    }

    public boolean isExists(AddInventoryDto addInventoryDto) {
        return inventoryRepository.existsInventoryByIsbn(addInventoryDto.getIsbn());
    }

    public void plusQuantity(AddInventoryDto addInventoryDto) {
        Inventory inventory = inventoryRepository.findInventoryByIsbn(addInventoryDto.getIsbn());
        inventory.plusQuantity(addInventoryDto.getQuantity());
    }

    public List<InventoryDtoForAdmin> findAllForAdmin() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> InventoryDtoForAdmin.from(inventory))
                .toList();
    }

    public List<InventoryDtoForUser> findAllForUser() {
        return inventoryRepository.findAll()
                .stream()
                .map(inventory -> InventoryDtoForUser.from(inventory))
                .toList();
    }

    public void update(UpdateInventoryDto updateInventoryDto) {
        Inventory inventory = inventoryRepository.findInventoryByInventoryId(updateInventoryDto.getInventoryId())
                .orElseThrow(() -> new NoSuchElementException());

        inventory.changeQuantity(updateInventoryDto.getQuantity());
        inventory.changeStatus(updateInventoryDto.getStatus());
    }
}
