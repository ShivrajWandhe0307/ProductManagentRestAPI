package com.example.ProductManagementRestAPI.ControllerTest;

import com.example.ProductManagementRestAPI.DTO.ProductsDTO;
import com.example.ProductManagementRestAPI.Entity.Products;
import com.example.ProductManagementRestAPI.ProductManagementRestApiApplication;
import com.example.ProductManagementRestAPI.Repository.ProductsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = ProductManagementRestApiApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class ProductsControllerH2Test {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Products product;

    @BeforeEach
    void setUp() {
        productsRepository.deleteAll();

        product = Products.builder()
                .productName("Laptop")
                .createdBy("testuser")
                .createdOn(LocalDateTime.now())
//                .modifiedBy("testuser")
//                .modifiedOn(LocalDateTime.now())
                .build();

        product = productsRepository.save(product);
    }

    @Test
    void testCreateProduct() throws Exception {
        ProductsDTO dto = new ProductsDTO();
        dto.setProductName("Phone");

        mockMvc.perform(post("/api/v1/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Phone"));
    }

    @Test
    void testGetProductById() throws Exception {
        mockMvc.perform(get("/api/v1/product/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Laptop"));
    }

    @Test
    void testGetAllProducts() throws Exception {
        mockMvc.perform(get("/api/v1/product?page=0&size=5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].productName").value("Laptop"));
    }

    @Test
    void testUpdateProduct() throws Exception {
        ProductsDTO dto = new ProductsDTO();
        dto.setProductName("UpdatedLaptop");

        mockMvc.perform(put("/api/v1/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("UpdatedLaptop"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/product/" + product.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string("Product Deleted with Id: " + product.getId()));
    }

    @Test
    void testDeleteNonExistingProduct() throws Exception {
        mockMvc.perform(delete("/api/v1/product/99999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Product Not Found"));
    }
}