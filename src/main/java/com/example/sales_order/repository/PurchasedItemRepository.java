package com.example.sales_order.repository;

import com.example.sales_order.entity.CatalogItem;
import com.example.sales_order.entity.PurchasedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedItemRepository extends JpaRepository<PurchasedItem, Long> {
}
