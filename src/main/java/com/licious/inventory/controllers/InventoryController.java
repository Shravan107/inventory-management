package com.licious.inventory.controllers;

import com.licious.inventory.dto.InventoryRequestDTO;
import com.licious.inventory.dto.InventoryResponseDTO;
import com.licious.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing inventory related operations.
 */
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;

    @PostMapping("/add")
    public ResponseEntity<InventoryResponseDTO> addInventory(@RequestBody InventoryRequestDTO request) {
        InventoryResponseDTO response = inventoryService.addInventory(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deduct")
    public ResponseEntity<InventoryResponseDTO> deductInventory(@RequestBody InventoryRequestDTO request) {
        InventoryResponseDTO response = inventoryService.deductInventory(request);
        return ResponseEntity.ok(response);
    }
}
