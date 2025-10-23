package com.example.sales_order.controller;

import com.example.sales_order.dto.SalesOrderRequestDTO;
import com.example.sales_order.dto.SalesOrderResponseDTO;
import com.example.sales_order.entity.SalesOrder;
import com.example.sales_order.mapper.SalesOrderMapper;
import com.example.sales_order.service.SalesOrderService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/salesorders")
public class SalesOrderController {
    private static final Logger log = LoggerFactory.getLogger(SalesOrderController.class);
    private final SalesOrderService service;
    private final SalesOrderMapper mapper;

    public SalesOrderController(SalesOrderService service, SalesOrderMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrderResponseDTO> getSalesOrderById(@PathVariable Long id) {
        log.info("Fetching sales order by id {} ", id);
        SalesOrder salesOrder = service.getSalesOrderById(id);
        return ResponseEntity.ok(mapper.toResponseDto(salesOrder));
    }

    @GetMapping
    public ResponseEntity<Page<SalesOrderResponseDTO>> getAllSalesOrders(Pageable pageable) {
        log.info("Fetching all the sales orders");
        Page<SalesOrderResponseDTO> pages = service.getAllSalesOrders(pageable)
                .map(mapper::toResponseDto);
        return ResponseEntity.ok(pages);
    }

    @PostMapping
    public ResponseEntity<SalesOrderResponseDTO> createSalesOrder(@Valid @RequestBody SalesOrderRequestDTO requestDTO) {
        log.info("Creating a sales order");
        SalesOrder salesOrder = service.createSalesOrder(mapper.toEntity(requestDTO));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(salesOrder));
    }

    @GetMapping("/filter/creation")
    public ResponseEntity<Page<SalesOrderResponseDTO>> filterByCreationDateRange(@RequestParam("startDate") LocalDate startDate,
                                                                                @RequestParam("endDate") LocalDate endDate,
                                                                                Pageable pageable) {
        log.info("Filtering the sales orders with creation date range {} and {} ", startDate, endDate);
        Page<SalesOrderResponseDTO> books = service
                .filterByCreationDateRange(startDate, endDate, pageable)
                .map(mapper::toResponseDto);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/filter/cancellation")
    public ResponseEntity<Page<SalesOrderResponseDTO>> filterByCancellationDateRange(@RequestParam("startDate") LocalDate startDate,
                                                                                    @RequestParam("endDate") LocalDate endDate,
                                                                                    Pageable pageable) {
        log.info("Filtering the sales orders with cancellation date range {} and {} ", startDate, endDate);
        Page<SalesOrderResponseDTO> books = service
                .filterByCancellationDateRange(startDate, endDate, pageable)
                .map(mapper::toResponseDto);
        return ResponseEntity.ok(books);
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelSalesOrder(@PathVariable Long id) {
        log.info("Cancelling the sales order with the id {} ", id);

        service.cancelSalesOrder(id);
        return ResponseEntity.noContent().build();
    }
}
