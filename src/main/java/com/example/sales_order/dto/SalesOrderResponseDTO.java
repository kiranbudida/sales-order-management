package com.example.sales_order.dto;

import com.example.sales_order.entity.PurchasedItem;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
public class SalesOrderResponseDTO {
    private Long id;
    private String customerName;
    private LocalDate creationDate;
    private LocalDate cancellationDate;

    private BigDecimal subTotal;
    private BigDecimal vat;
    private BigDecimal total;

    private List<PurchasedItem> purchasedItemsList = new ArrayList<>();

}
