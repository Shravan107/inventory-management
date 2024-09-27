package com.licious.inventory.services;

import com.licious.inventory.dto.InventoryRequestDTO;
import com.licious.inventory.dto.InventoryResponseDTO;

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


}
