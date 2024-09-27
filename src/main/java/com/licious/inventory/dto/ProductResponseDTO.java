package com.licious.inventory.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponseDTO {
    private Long id;
    private String name;
    private Double price;
    private int maxQuantity;
}
