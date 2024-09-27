package com.licious.inventory.services;

import com.licious.inventory.dto.InventoryRequest;
import com.licious.inventory.dto.InventoryResponse;
import com.licious.inventory.entities.Inventory;
import com.licious.inventory.entities.InventoryTransaction;
import com.licious.inventory.exceptions.ConcurrencyException;
import com.licious.inventory.exceptions.InsufficientStockException;
import com.licious.inventory.exceptions.InventoryNotFoundException;
import com.licious.inventory.exceptions.ProductNotFoundException;
import com.licious.inventory.exceptions.TransactionsNotFoundException;
import com.licious.inventory.repositories.InventoryRepository;
import com.licious.inventory.repositories.InventoryTransactionRepository;
import com.licious.inventory.util.TransactionType;
import jakarta.persistence.OptimisticLockException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public InventoryResponse addInventory(InventoryRequest request) {
        try {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getProductId());
            if (inventoryOpt.isEmpty()) {
                throw new ProductNotFoundException("Product not found in inventory for the given id.");
            }

            Inventory inventory = inventoryOpt.get();
            int newQuantity = inventory.getQuantity() + request.getQuantity();
            if (newQuantity > inventory.getProduct().getMaxQuantity()) {
                throw new IllegalArgumentException("Exceeds maximum allowed inventory.");
            }

            inventory.setQuantity(newQuantity);
            inventoryRepository.save(inventory);

            logTransaction(request.getProductId(), TransactionType.ADDITION, request.getQuantity());
            return new InventoryResponse("Inventory added successfully.");
        } catch (OptimisticLockException e) {
            throw new ConcurrencyException("Inventory was updated by another transaction. Please retry.");
        }

    }

    // Method to deduct inventory when order fulfillment.
    @Transactional
    @Override
    public InventoryResponse deductInventory(InventoryRequest request) {
        try {
            Optional<Inventory> inventoryOpt = inventoryRepository.findById(request.getProductId());
            if (inventoryOpt.isEmpty()) {
                throw new ProductNotFoundException("Product not found in inventory for the given id.");
            }

            Inventory inventory = inventoryOpt.get();
            if (inventory.getQuantity() < request.getQuantity()) {
                throw new InsufficientStockException("Not enough stock for deduction.");
            }

            inventory.setQuantity(inventory.getQuantity() - request.getQuantity());
            inventoryRepository.save(inventory);

            logTransaction(request.getProductId(), TransactionType.DEDUCTION, request.getQuantity());

            return new InventoryResponse("Inventory deducted successfully.");
        } catch (OptimisticLockException e) {
            return new InventoryResponse("Inventory was deducted by another transaction. Please retry.");
        }
    }

    public List<Inventory> getAllInventory() {
        List<Inventory> inventoryList = inventoryRepository.findAll();

        if (inventoryList.isEmpty()) {
            throw new InventoryNotFoundException("No inventory data found.");
        }

        return inventoryList;
    }

    public Inventory getInventory(Long productId) {
        Optional<Inventory> inventoryOpt = inventoryRepository.findById(productId);

        if (inventoryOpt.isEmpty()) {
            throw new InventoryNotFoundException("No inventory data found for product id " + productId);
        }

        return inventoryOpt.get();
    }

    public List<InventoryTransaction> getTransactionsByProductId(Long productId) {
        List<InventoryTransaction> transactions = transactionRepository.findByProductId(productId);

        if (transactions.isEmpty()) {
            throw new TransactionsNotFoundException("No transactions found wit product id " + productId);
        }

        return transactions;
    }

    public List<InventoryTransaction> getAllTransactions() {
        List<InventoryTransaction> transactions = transactionRepository.findAll();

        if (transactions.isEmpty()) {
            throw new TransactionsNotFoundException("No inventory transactions found.");
        }

        return transactions;
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
