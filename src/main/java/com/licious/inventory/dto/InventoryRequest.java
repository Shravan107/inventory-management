package com.licious.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InventoryRequest {
    private Long productId;
    private int quantity;
}
