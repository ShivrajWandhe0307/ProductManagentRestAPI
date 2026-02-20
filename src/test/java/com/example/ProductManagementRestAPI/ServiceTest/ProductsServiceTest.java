package com.example.ProductManagementRestAPI.ServiceTest;

import com.example.ProductManagementRestAPI.DTO.ProductsDTO;
import com.example.ProductManagementRestAPI.Entity.Products;
import com.example.ProductManagementRestAPI.Repository.ProductsRepository;
import com.example.ProductManagementRestAPI.Service.IProductService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductsServiceTest {

    @InjectMocks
    private IProductService productsService;

    @Mock
    private ProductsRepository productsRepository;

    private Products product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Products();
        product.setId(1L);
        product.setProductName("Laptop");
        product.setCreatedBy("test");           // Ensure non-null for DB
        product.setCreatedOn(LocalDateTime.now()); // Ensure non-null for DB
    }

    @Test
    void testCreateProduct() {
        ProductsDTO dto = new ProductsDTO();
        dto.setProductName("Laptop");

        when(productsRepository.save(any(Products.class))).thenReturn(product);

        ProductsDTO created = productsService.newProduct(dto);
        assertNotNull(created);
        assertEquals("Laptop", created.getProductName());
        verify(productsRepository, times(1)).save(any(Products.class));
    }

    @Test
    void testGetProduct() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));

        ProductsDTO dto = productsService.getProduct(1L).orElse(null);
        assertNotNull(dto);
        assertEquals("Laptop", dto.getProductName());
    }

    @Test
    void testGetProduct_NotFound() {
        when(productsRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<ProductsDTO> dto = productsService.getProduct(99L);
        assertTrue(dto.isEmpty());
    }

    @Test
    void testUpdateProduct() {
        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productsRepository.save(any(Products.class))).thenAnswer(invocation -> invocation.getArgument(0));

        ProductsDTO dto = new ProductsDTO();
        dto.setProductName("Gaming Laptop");

        ProductsDTO updated = productsService.updateProduct(1L, dto);
        assertNotNull(updated);
        assertEquals("Gaming Laptop", updated.getProductName());
    }

    @Test
    void testDeleteProduct() {
        when(productsRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productsRepository).deleteById(1L);

        Boolean status = productsService.deleteProduct(1L);
        assertTrue(status);

        verify(productsRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_NotFound() {
        when(productsRepository.existsById(99L)).thenReturn(false);

        Boolean status = productsService.deleteProduct(99L);
        assertFalse(status);

        verify(productsRepository, never()).deleteById(99L);
    }

    @Test
    void testGetAllProducts() {
        Page<Products> page = new PageImpl<>(List.of(product));
        when(productsRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        Page<ProductsDTO> products = productsService.getAllProducts(0, 10);
        assertNotNull(products);
        assertEquals(1, products.getTotalElements());
        assertEquals("Laptop", products.getContent().get(0).getProductName());
    }
}