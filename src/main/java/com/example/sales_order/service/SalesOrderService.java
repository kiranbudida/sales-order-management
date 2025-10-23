package com.example.sales_order.service;

import com.example.sales_order.entity.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface SalesOrderService {
    SalesOrder createSalesOrder(SalesOrder salesOrder);
    SalesOrder getSalesOrderById(Long id);
    Page<SalesOrder> getAllSalesOrders(Pageable pageable);
    void cancelSalesOrder(Long id);
    Page<SalesOrder> filterByCreationDateRange(LocalDate startDate, LocalDate endDate,
                                               Pageable pageable);
    Page<SalesOrder> filterByCancellationDateRange(LocalDate startDate, LocalDate endDate,
                                                   Pageable pageable);
}
