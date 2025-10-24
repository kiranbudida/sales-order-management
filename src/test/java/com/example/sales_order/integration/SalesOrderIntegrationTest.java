package com.example.sales_order.integration;

import com.example.sales_order.SalesOrderApplication;
import com.example.sales_order.dto.PurchasedItemDTO;
import com.example.sales_order.dto.SalesOrderRequestDTO;
import com.example.sales_order.entity.SalesOrder;
import com.example.sales_order.repository.SalesOrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest(classes = SalesOrderApplication.class)
@AutoConfigureMockMvc
@WithMockUser(username = "testuser", roles = "ADMIN")
public class SalesOrderIntegrationTest {

    static {
        System.setProperty("user.timezone", "Asia/Kolkata");
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("postgres")
            .withPassword("postgres")
            .withEnv("TZ", "Asia/Kolkata");

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresContainer::getUsername);
        registry.add("spring.datasource.password", postgresContainer::getPassword);
        registry.add("spring.jpa.properties.hibernate.jdbc.time_zone", () -> "Asia/Kolkata");
    }

    @Test
    void testCreateSalesOrderIntegration() throws Exception {
        // Given
        PurchasedItemDTO item1 = new PurchasedItemDTO("Keyboard", new BigDecimal("200.50"), 1);
        PurchasedItemDTO item2 = new PurchasedItemDTO("Mouse", new BigDecimal("50.00"), 2);
        SalesOrderRequestDTO requestDTO = new SalesOrderRequestDTO(null, "John Doe", List.of(item1, item2));

        // When + Then
        mockMvc.perform(post("/salesorders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerName").value("John Doe"))
                .andExpect(jsonPath("$.purchasedItemsList[0].itemName").value("Keyboard"));

        // Verify DB
        List<SalesOrder> allOrders = salesOrderRepository.findAll();
        assertThat(allOrders).hasSize(1);
        assertThat(allOrders.get(0).getCustomerName()).isEqualTo("John Doe");
    }
}
