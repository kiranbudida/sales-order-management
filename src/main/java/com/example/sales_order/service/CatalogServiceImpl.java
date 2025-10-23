package com.example.sales_order.service;

import com.example.sales_order.entity.CatalogItem;
import com.example.sales_order.exception.ResourceNotFoundException;
import com.example.sales_order.repository.CatalogItemRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CatalogServiceImpl implements CatalogService {

    private CatalogItemRepository repo;

    public CatalogServiceImpl(CatalogItemRepository repo) {
        this.repo = repo;
    }
    @Override
    public CatalogItem getCatalogById(Long id) {
        return repo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Catalog item is not found with the id: "+id));
    }

    @Override
    public Page<CatalogItem> getAllCatalogItems(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public CatalogItem updateCatalog(Long id, CatalogItem item) {
        return repo.findById(id)
                .map(
                e->{
                    e.setCurrentPrice(item.getCurrentPrice());
                    e.setName(item.getName());
                    return repo.save(e);
                })
                .orElseThrow(()-> new ResourceNotFoundException("Catalog item is not found with the id: "+id));
    }

    @Override
    public CatalogItem createCatalogItem(CatalogItem item) {
        return repo.save(item);
    }
}
