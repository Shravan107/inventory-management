package com.licious.inventory.services;

import com.licious.inventory.dto.InventoryRequest;
import com.licious.inventory.dto.InventoryResponse;
import com.licious.inventory.entities.Inventory;
import com.licious.inventory.entities.InventoryTransaction;

import java.util.List;

public interface InventoryService {

    /**
     * Method to add inventory.
     *
     * @param request InventoryRequestDTO object.
     * @return InventoryResponseDTO object.
     **/
    InventoryResponse addInventory(InventoryRequest request);

    /**
     * Method to deduct inventory.
     *
     * @param request InventoryRequestDTO object.
     * @return InventoryResponseDTO object.
     **/
    InventoryResponse deductInventory(InventoryRequest request);

    /**
     * Method to get all inventory data.
     *
     * @return List of Inventory objects.
     **/
    List<Inventory> getAllInventory();

    /**
     * Method to get inventory by product id.
     * @param productId Long object.
     * @return Inventory object.
     **/
    Inventory getInventory(Long productId);

    /**
     * Method to get transactions by product id.
     *
     * @param productId Long object.
     * @return List of InventoryTransaction objects.
     **/
    List<InventoryTransaction> getTransactionsByProductId(Long productId);

    /**
     * Method to get all transactions.
     *
     * @return List of InventoryTransaction objects.
     **/
    List<InventoryTransaction> getAllTransactions();

}
