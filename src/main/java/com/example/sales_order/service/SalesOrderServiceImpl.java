package com.example.sales_order.service;

import com.example.sales_order.entity.SalesOrder;
import com.example.sales_order.exception.OrderAlreadyCancelledException;
import com.example.sales_order.exception.ResourceNotFoundException;
import com.example.sales_order.repository.SalesOrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@Transactional
public class SalesOrderServiceImpl implements SalesOrderService {
    private final SalesOrderRepository repo;

    public SalesOrderServiceImpl(SalesOrderRepository repository) {
        this.repo = repository;
    }

    @Override
    public SalesOrder createSalesOrder(SalesOrder order) {

        order.setCreationDate(LocalDate.now());

        // Set parent in items
        order.setItemsParent();

        // Calculate subTotal
        BigDecimal subTotal = order.getPurchasedItemsList().stream()
                .map(item -> item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        order.setSubTotal(subTotal);

        // Example VAT 10%
        BigDecimal vat = subTotal.multiply(BigDecimal.valueOf(0.1));
        order.setVat(vat);

        // Total
        order.setTotal(subTotal.add(vat));

        return repo.save(order);
    }

    @Override
    public SalesOrder getSalesOrderById(Long id) {
        return repo.findById(id)
                .orElseThrow(()->
                        new ResourceNotFoundException("Resource is not found with the id: "+id));
    }

    @Override
    public Page<SalesOrder> getAllSalesOrders(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public void cancelSalesOrder(Long id) {
        SalesOrder salesOrder = repo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Resource is not found with the id: "+id));

        if (salesOrder.getCancellationDate() != null) {
            throw new OrderAlreadyCancelledException("Sales order is already cancelled on: "
                    + salesOrder.getCancellationDate());
        }

        salesOrder.setCancellationDate(LocalDate.now());

        repo.save(salesOrder);
    }

    @Override
    public Page<SalesOrder> filterByCreationDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return repo.findByCreationDateBetween(startDate, endDate, pageable);
    }

    @Override
    public Page<SalesOrder> filterByCancellationDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return repo.findByCancellationDateBetween(startDate, endDate, pageable);
    }
}
