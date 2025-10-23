package com.example.sales_order.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasedItemDTO {
    @NotBlank(message = "Item name should not be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Item name should contain only alphabets and spaces")
    private String itemName;

    @DecimalMin(value = "0.0", message = "price should be a positive number")
    private BigDecimal price;

    @Min(value = 1, message = "quantity should be at least 1")
    private Integer quantity;

}
