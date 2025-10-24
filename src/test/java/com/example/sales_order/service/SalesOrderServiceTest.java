package com.example.sales_order.service;

import com.example.sales_order.entity.PurchasedItem;
import com.example.sales_order.entity.SalesOrder;
import com.example.sales_order.repository.SalesOrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class SalesOrderServiceTest {

    @Mock
    private SalesOrderRepository salesOrderRepository;

    @InjectMocks
    private SalesOrderServiceImpl salesOrderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSalesOrder_CalculatesTotalsCorrectly() {
        // Given
        PurchasedItem item1 = new PurchasedItem(null, "Keyboard", new BigDecimal("200.50"), 2, null);
        PurchasedItem item2 = new PurchasedItem(null, "Mouse", new BigDecimal("100.00"), 1, null);

        SalesOrder inputOrder = new SalesOrder();
        inputOrder.setCustomerName("John Doe");
        inputOrder.setPurchasedItemsList(List.of(item1, item2));

        SalesOrder savedOrder = new SalesOrder();
        savedOrder.setId(1L);
        savedOrder.setCustomerName("John Doe");
        savedOrder.setPurchasedItemsList(List.of(item1, item2));
        savedOrder.setCreationDate(LocalDate.now());
        savedOrder.setSubTotal(new BigDecimal("501.00"));
        savedOrder.setVat(new BigDecimal("50.10"));
        savedOrder.setTotal(new BigDecimal("551.10"));

        when(salesOrderRepository.save(any(SalesOrder.class))).thenReturn(savedOrder);

        // When
        SalesOrder result = salesOrderService.createSalesOrder(inputOrder);

        // Then
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCustomerName()).isEqualTo("John Doe");
        assertThat(result.getSubTotal()).isEqualByComparingTo("501.00");
        assertThat(result.getVat()).isEqualByComparingTo("50.10");
        assertThat(result.getTotal()).isEqualByComparingTo("551.10");

        verify(salesOrderRepository, times(1)).save(any(SalesOrder.class));
    }
}
