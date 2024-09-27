package com.licious.inventory.services;

import com.licious.inventory.dto.InventoryRequestDTO;
import com.licious.inventory.dto.InventoryResponseDTO;
import com.licious.inventory.entities.Inventory;
import com.licious.inventory.entities.InventoryTransaction;

import java.util.List;

public interface InventoryService {

    /**
     * Method to add inventory.
     * @param request InventoryRequestDTO object.
     * @return InventoryResponseDTO object.
     **/
    InventoryResponseDTO addInventory(InventoryRequestDTO request);

    /**
     * Method to deduct inventory.
     * @param request InventoryRequestDTO object.
     * @return InventoryResponseDTO object.
     **/
    InventoryResponseDTO deductInventory(InventoryRequestDTO request);

    /**
     * Method to get all inventory data.
     * @return List of Inventory objects.
     **/
    List<Inventory> getAllInventory();

    /**
     * Method to get transactions by product id.
     * @param productId Long object.
     * @return List of InventoryTransaction objects.
     **/
    List<InventoryTransaction> getTransactionsByProductId(Long productId);

    /**
     * Method to get all transactions.
     * @return List of InventoryTransaction objects.
     **/
    List<InventoryTransaction> getAllTransactions();


}
