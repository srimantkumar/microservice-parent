package com.srimant.inventoryservice.controller;

import com.srimant.inventoryservice.dto.InventoryResponse;
import com.srimant.inventoryservice.model.Inventory;
import com.srimant.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isInStock(@RequestParam List<String> skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/allProducts")
    public List<Inventory> getAllInventoryProducts() {
        return inventoryService.getAllInventoryProducts();
    }
}

