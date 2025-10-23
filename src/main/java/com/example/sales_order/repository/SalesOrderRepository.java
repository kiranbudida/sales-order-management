package com.example.sales_order.repository;

import com.example.sales_order.entity.SalesOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Long> {
    Page<SalesOrder> findByCreationDateBetween(LocalDate startDate, LocalDate endDate,
                                               Pageable pageable);
    Page<SalesOrder> findByCancellationDateBetween(LocalDate startDate, LocalDate endDate,
                                                   Pageable pageable);
}
