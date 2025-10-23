package com.example.sales_order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CatalogItemDTO {
    @NotBlank(message = "Item name should not be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Item name should contain only alphabets and spaces")
    private String name;

    @DecimalMin(value = "0.0", message = "current price should be a positive number")
    private BigDecimal currentPrice;
}
