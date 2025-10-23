package com.example.sales_order.controller;

import com.example.sales_order.dto.CatalogItemDTO;
import com.example.sales_order.entity.CatalogItem;
import com.example.sales_order.mapper.CatalogMapper;
import com.example.sales_order.service.CatalogService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/catalogitems")
public class CatalogController {
    private static final Logger log = LoggerFactory.getLogger(CatalogController.class);
    private final CatalogService service;
    private final CatalogMapper mapper;

    public CatalogController(CatalogService service, CatalogMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogItemDTO> getCatalogItemById(@PathVariable Long id) {
        log.info("Fetching catalog by id {} ", id);
        CatalogItem catalogItem = service.getCatalogById(id);
        return ResponseEntity.ok(mapper.toDto(catalogItem));
    }

    @GetMapping
    public ResponseEntity<Page<CatalogItemDTO>> getAllCatalogItems(Pageable pageable) {
        log.info("Fetching all the catalog items");
        Page<CatalogItemDTO> pages = service.getAllCatalogItems(pageable)
                .map(mapper::toDto);
        return ResponseEntity.ok(pages);
    }

    @PostMapping
    public ResponseEntity<CatalogItemDTO> createCatalogItem(@Valid @RequestBody CatalogItemDTO catalogItemDto) {
        log.info("Creating a catalog item");
        CatalogItem catalogItem = service.createCatalogItem(mapper.toEntity(catalogItemDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toDto(catalogItem));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatalogItemDTO> updateCatalogItem(@PathVariable Long id,
                                                           @Valid @RequestBody CatalogItemDTO catalogItemDto) {
        log.info("Updating the catalog item with the id {} ", id);

        CatalogItem updatedCatalog = service.updateCatalog(id, mapper.toEntity(catalogItemDto));
        return ResponseEntity.ok(mapper.toDto(updatedCatalog));
    }
}
