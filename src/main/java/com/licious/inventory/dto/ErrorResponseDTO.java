package com.licious.inventory.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponseDTO {
    private int status;
    private String message;
}
