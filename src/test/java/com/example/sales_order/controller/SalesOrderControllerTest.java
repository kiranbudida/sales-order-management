package com.example.sales_order.controller;

import com.example.sales_order.dto.PurchasedItemDTO;
import com.example.sales_order.dto.SalesOrderRequestDTO;
import com.example.sales_order.entity.PurchasedItem;
import com.example.sales_order.entity.SalesOrder;
import com.example.sales_order.mapper.SalesOrderMapper;
import com.example.sales_order.service.SalesOrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SalesOrderController.class,
    excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class SalesOrderControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private SalesOrderService salesOrderService;
    @Autowired
    private ObjectMapper objectMapper;
    @MockitoBean
    private SalesOrderMapper salesOrderMapper;

    @Test
    void testCreateSalesOrder() throws Exception {

        PurchasedItemDTO purchasedItemDTO =
                new PurchasedItemDTO("Keyboard", new BigDecimal("200.50"), 1);
        List<PurchasedItemDTO> purchasedItemsList = List.of(purchasedItemDTO);

        SalesOrderRequestDTO salesOrderRequestDTO =
                new SalesOrderRequestDTO(1L, "Customer", purchasedItemsList);

        PurchasedItem purchasedItem = new PurchasedItem(1L, "Keyboard", new BigDecimal("200.50"), 1, null);
        List<PurchasedItem> items = new ArrayList<>();
        items.add(purchasedItem);

        SalesOrder salesOrder = new SalesOrder();
        salesOrder.setId(1L);
        salesOrder.setCustomerName("Customer");
        salesOrder.setPurchasedItemsList(items);
        salesOrder.setCreationDate(LocalDate.now());


        when(salesOrderService.createSalesOrder(any(SalesOrder.class)))
                .thenReturn(salesOrder);

        mockMvc.perform(post("/salesorders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(salesOrderRequestDTO)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.customerName").value("Customer"))
                .andExpect(jsonPath("$.purchasedItems[0].itemName").value("Keyboard"));
    }
}
