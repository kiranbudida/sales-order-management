package com.example.sales_order.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderRequestDTO {

    private Long id;

    @NotBlank(message = "Customer name should not be empty")
    @Pattern(regexp = "^[a-zA-Z\\s]+$", message = "Customer name should contain only alphabets and spaces")
    private String customerName;

    @Valid
    @NotEmpty(message = "Purchased items list can not be empty")
    private List<PurchasedItemDTO> purchasedItemsList = new ArrayList<>();

}
