package com.srimant.inventoryservice.service;

import com.srimant.inventoryservice.dto.InventoryResponse;
import com.srimant.inventoryservice.model.Inventory;
import com.srimant.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCode) {
        return inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .quantityInStock(inventory.getQuantity())
                            .build()
                ).toList();
    }

    public List<Inventory> getAllInventoryProducts() {
        return inventoryRepository.findAll();
    }
}
