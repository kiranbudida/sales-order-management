package com.example.sales_order.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchased_items")
public class PurchasedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private BigDecimal price;
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "sales_order_id")
    @JsonBackReference
    private SalesOrder salesOrder;

}
