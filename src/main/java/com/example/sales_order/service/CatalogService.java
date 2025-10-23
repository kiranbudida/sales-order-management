package com.example.sales_order.service;

import com.example.sales_order.entity.CatalogItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CatalogService {
    CatalogItem getCatalogById(Long id);
    Page<CatalogItem> getAllCatalogItems(Pageable pageable);
    CatalogItem updateCatalog(Long id, CatalogItem item);
    CatalogItem createCatalogItem(CatalogItem item);
}
