package com.example.ProductManagementRestAPI.ServiceTest;

import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import com.example.ProductManagementRestAPI.Entity.Items;
import com.example.ProductManagementRestAPI.Entity.Products;
import com.example.ProductManagementRestAPI.Repository.ItemsRepository;
import com.example.ProductManagementRestAPI.Repository.ProductsRepository;
import com.example.ProductManagementRestAPI.Service.IItemsService;
import com.example.ProductManagementRestAPI.Service.ItemsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ItemsServiceTest {

    @InjectMocks
    private IItemsService itemsService;

    @Mock
    private ItemsRepository itemsRepository;

    @Mock
    private ProductsRepository productsRepository;

    private Products product;
    private Items item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        product = new Products();
        product.setId(1L);
        product.setProductName("Laptop");
        product.setCreatedBy("test");
        product.setCreatedOn(java.time.LocalDateTime.now());

        item = new Items();
        item.setId(1L);
        item.setQuantity(5);
        item.setProduct(product);
    }

    @Test
    void testCreateItem() {

        ItemsDTO dto = new ItemsDTO();
        dto.setProductId(1L);
        dto.setQuantity(5);


        when(productsRepository.findById(1L)).thenReturn(Optional.of(product));
        when(itemsRepository.save(any(Items.class))).thenReturn(item);


        ItemsDTO created = itemsService.newItems(dto);


        assertNotNull(created);
        assertEquals(5, created.getQuantity());
        assertEquals(1L, created.getProductId());


        verify(productsRepository, times(1)).findById(1L);
        verify(itemsRepository, times(1)).save(any(Items.class));
    }

    @Test
    void testAllItems() {

        Page<Items> page = new PageImpl<>(List.of(item));
        when(itemsRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);


        Page<ItemsDTO> itemsPage = itemsService.allItems(0, 10);


        assertNotNull(itemsPage);
        assertEquals(1, itemsPage.getTotalElements());
        assertEquals(5, itemsPage.getContent().get(0).getQuantity());
    }


}