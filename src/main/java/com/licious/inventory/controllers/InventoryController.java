package com.licious.inventory.controllers;

import com.licious.inventory.dto.InventoryRequest;
import com.licious.inventory.dto.InventoryResponse;
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
    public ResponseEntity<InventoryResponse> addInventory(@RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.addInventory(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/deduct")
    public ResponseEntity<InventoryResponse> deductInventory(@RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.deductInventory(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Inventory>> getInventory() {
        List<Inventory> inventoryList = inventoryService.getAllInventory();
        return ResponseEntity.ok(inventoryList);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        Inventory inventory = inventoryService.getInventory(productId);
        return ResponseEntity.ok(inventory);
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
