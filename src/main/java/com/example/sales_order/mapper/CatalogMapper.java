package com.example.sales_order.mapper;

import com.example.sales_order.dto.CatalogItemDTO;
import com.example.sales_order.entity.CatalogItem;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatalogMapper {
    CatalogItemDTO toDto(CatalogItem catalogItem);
    CatalogItem toEntity(CatalogItemDTO catalogItemDTO);
}
