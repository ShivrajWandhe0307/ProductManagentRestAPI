package com.example.ProductManagementRestAPI.Service;

import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import com.example.ProductManagementRestAPI.Entity.Items;
import com.example.ProductManagementRestAPI.Entity.Products;
import com.example.ProductManagementRestAPI.Exception.ResourceNotFound;
import com.example.ProductManagementRestAPI.Repository.ItemsRepository;
import com.example.ProductManagementRestAPI.Repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Slf4j
public class IItemsService implements ItemsService{


    private final ItemsRepository itemsRepository;

    private final ProductsRepository productsRepository;
    @Override
    public ItemsDTO newItems(ItemsDTO itemsDTO) {

        log.info("Creating item for product id: {}", itemsDTO.getProductId());

        Products product = productsRepository.findById(itemsDTO.getProductId())
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", itemsDTO.getProductId());
                    return new ResourceNotFound(
                            "Product Not Found With Id: " + itemsDTO.getProductId()
                    );
                });

        Items items = Items.builder()
                .quantity(itemsDTO.getQuantity())
                .product(product)
                .build();

        items = itemsRepository.save(items);

        log.info("Item created successfully with id: {}", items.getId());

        return ItemsDTO.fromEntity(items);
    }

    @Override
    public Page<ItemsDTO> allItems(int page, int size) {
        Pageable pageable= PageRequest.of(page,size);
        Page<Items> items=itemsRepository.findAll(pageable);
        return items.map(ItemsDTO::fromEntity);
    }
}
