package com.example.sales_order.mapper;

import com.example.sales_order.dto.SalesOrderRequestDTO;
import com.example.sales_order.dto.SalesOrderResponseDTO;
import com.example.sales_order.entity.SalesOrder;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SalesOrderMapper {
    SalesOrder toEntity(SalesOrderRequestDTO requestDTO);
    SalesOrderResponseDTO toResponseDto(SalesOrder salesOrder);
}
