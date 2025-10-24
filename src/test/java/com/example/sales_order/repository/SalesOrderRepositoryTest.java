package com.example.sales_order.repository;

import com.example.sales_order.entity.PurchasedItem;
import com.example.sales_order.entity.SalesOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SalesOrderRepositoryTest {

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private PurchasedItemRepository purchasedItemRepository;

    private SalesOrder order;

    @BeforeEach
    void setUp() {
        PurchasedItem item = new PurchasedItem();
        item.setItemName("Keyboard");
        item.setPrice(new BigDecimal("250.50"));
        item.setQuantity(1);

        order = new SalesOrder();
        order.setCustomerName("John Doe");
        order.setCreationDate(LocalDate.now());
        order.setPurchasedItemsList(List.of(item));

        item.setSalesOrder(order);
    }

    @Test
    @Order(1)
    void testSaveSalesOrder() {
        SalesOrder savedOrder = salesOrderRepository.save(order);

        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getCustomerName()).isEqualTo("John Doe");
        assertThat(savedOrder.getPurchasedItemsList()).hasSize(1);
    }

    @Test
    @Order(2)
    void testFindById() {
        SalesOrder savedOrder = salesOrderRepository.save(order);

        Optional<SalesOrder> foundOrder = salesOrderRepository.findById(savedOrder.getId());

        assertThat(foundOrder).isPresent();
        assertThat(foundOrder.get().getCustomerName()).isEqualTo("John Doe");
    }

    @Test
    @Order(3)
    void testFindByCreationDateBetween() {
        SalesOrder savedOrder = salesOrderRepository.save(order);

        LocalDate start = LocalDate.now().minusDays(1);
        LocalDate end = LocalDate.now().plusDays(1);
        Pageable pageable = PageRequest.of(0, 10); // first page, 10 records per page

        Page<SalesOrder> ordersPage = salesOrderRepository.findByCreationDateBetween(start, end, pageable);

        assertThat(ordersPage.getContent()).hasSize(1);
        assertThat(ordersPage.getContent().stream()
                .anyMatch(o->o.getCustomerName().equals("John Doe"))).isTrue();
    }
}
