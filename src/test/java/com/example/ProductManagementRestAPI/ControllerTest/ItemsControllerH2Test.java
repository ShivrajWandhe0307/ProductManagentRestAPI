package com.example.ProductManagementRestAPI.ControllerTest;

import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import com.example.ProductManagementRestAPI.Entity.Products;
import com.example.ProductManagementRestAPI.ProductManagementRestApiApplication;
import com.example.ProductManagementRestAPI.Repository.ItemsRepository;
import com.example.ProductManagementRestAPI.Repository.ProductsRepository;
import com.example.ProductManagementRestAPI.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = {ProductManagementRestApiApplication.class, TestSecurityConfig.class})
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ItemsControllerH2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ItemsRepository itemsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Products product;

    @BeforeEach
    void setUp() {
        itemsRepository.deleteAll();
        productsRepository.deleteAll();

        // Create a product for the item to reference
        product = Products.builder()
                .productName("Laptop")
                .createdBy("testuser")
                .createdOn(LocalDateTime.now())
                .modifiedBy("testuser")
                .modifiedOn(LocalDateTime.now())
                .build();

        product = productsRepository.save(product);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void testCreateItem() throws Exception {
        ItemsDTO dto = new ItemsDTO();
        dto.setQuantity(5);
        dto.setProductId(product.getId()); // make sure you have a valid product saved in H2

        mockMvc.perform(post("/api/v1/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.quantity").value(5));
    }
}