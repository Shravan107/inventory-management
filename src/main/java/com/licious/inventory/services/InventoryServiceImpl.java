package com.licious.inventory.services;

import com.licious.inventory.dto.InventoryRequestDTO;
import com.licious.inventory.dto.InventoryResponseDTO;
import com.licious.inventory.entities.Inventory;
import com.licious.inventory.entities.InventoryTransaction;
import com.licious.inventory.exceptions.InsufficientStockException;
import com.licious.inventory.exceptions.ProductNotFoundException;
import com.licious.inventory.repositories.InventoryRepository;
import com.licious.inventory.repositories.InventoryTransactionRepository;
import com.licious.inventory.util.TransactionType;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

/**
 * Service class for managing inventory.
 */
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryTransactionRepository transactionRepository;

    // Method to add inventory when restocking or returns.
    @Transactional
    @Override
    public synchronized InventoryResponseDTO addInventory(InventoryRequestDTO request) {
        try {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getProductId());
            if (inventoryOpt.isEmpty()) {
                throw new ProductNotFoundException("Product not found.");
            }

            Inventory inventory = inventoryOpt.get();
            int newQuantity = inventory.getQuantity() + request.getQuantity();
            if (newQuantity > inventory.getProduct().getMaxQuantity()) {
                throw new IllegalArgumentException("Exceeds maximum allowed inventory.");
            }

            inventory.setQuantity(newQuantity);
            inventoryRepository.save(inventory);

            logTransaction(request.getProductId(), TransactionType.ADDITION, request.getQuantity());
            return new InventoryResponseDTO("Inventory added successfully.");
        } catch (OptimisticLockException e) {
            return new InventoryResponseDTO("Inventory was updated by another transaction. Please retry.");
        }

    }

    // Method to deduct inventory when order fulfillment.
    @Transactional
    @Override
    public synchronized InventoryResponseDTO deductInventory(InventoryRequestDTO request) {
        try {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getProductId());
            if (inventoryOpt.isEmpty()) {
                throw new ProductNotFoundException("Product not found.");
            }

            Inventory inventory = inventoryOpt.get();
            if (inventory.getQuantity() < request.getQuantity()) {
                throw new InsufficientStockException("Not enough stock.");
            }

            inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
            inventoryRepository.save(inventory);

            logTransaction(request.getProductId(), TransactionType.DEDUCTION, request.getQuantity());

            return new InventoryResponseDTO("Inventory deducted successfully.");
        } catch (OptimisticLockException e) {
            return new InventoryResponseDTO("Inventory was deducted by another transaction. Please retry.");
        }
    }

    private void logTransaction(Long productId, TransactionType type, int quantity) {
        InventoryTransaction transaction = new InventoryTransaction();
        transaction.setProductId(productId);
        transaction.setTransactionType(type);
        transaction.setQuantity(quantity);
        transaction.setTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }
}
