package com.example.sales_order.dto;

import com.example.sales_order.entity.PurchasedItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalesOrderResponseDTO {
    private Long id;
    private String customerName;
    private LocalDate creationDate;
    private LocalDate cancellationDate;

    private BigDecimal subTotal;
    private BigDecimal vat;
    private BigDecimal total;

    private List<PurchasedItemDTO> purchasedItemsList = new ArrayList<>();

}
