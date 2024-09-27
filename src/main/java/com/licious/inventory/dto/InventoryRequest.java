package com.licious.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryRequest {
    private Long productId;
    private int quantity;
}
