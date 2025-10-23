package com.example.sales_order.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "sales_orders")
public class SalesOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName;
    private LocalDate creationDate;
    private LocalDate cancellationDate;

    private BigDecimal subTotal;
    private BigDecimal vat;
    private BigDecimal total;

    @OneToMany(mappedBy = "salesOrder", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PurchasedItem> purchasedItemsList = new ArrayList<>();

    // Helper method to set parent in items
    public void setItemsParent() {
        if (purchasedItemsList != null) {
            purchasedItemsList.forEach(item -> item.setSalesOrder(this));
        }
    }
}
