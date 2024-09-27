package com.licious.inventory.controllers;

import com.licious.inventory.dto.InventoryRequestDTO;
import com.licious.inventory.dto.InventoryResponseDTO;
import com.licious.inventory.entities.Inventory;
import com.licious.inventory.entities.InventoryTransaction;
import com.licious.inventory.services.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getInventory() {
        List<Inventory> inventoryList = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/transactions/{productId}")
    public ResponseEntity<List<InventoryTransaction>> getTransactionsByProductId(@PathVariable Long productId) {
        List<InventoryTransaction> transactions = inventoryService.getTransactionsByProductId(productId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transactions/all")
    public ResponseEntity<List<InventoryTransaction>> getAllTransactions() {
        List<InventoryTransaction> transactions = inventoryService.getAllTransactions();
        return ResponseEntity.ok(transactions);
    }
}
