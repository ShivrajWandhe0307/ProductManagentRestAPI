package com.example.ProductManagementRestAPI.Service;

import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import com.example.ProductManagementRestAPI.DTO.ProductsDTO;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class IProductService implements ProductsService{

    private final ProductsRepository productsRepository;
    private final ItemsRepository itemsRepository;


    @Override
    public ProductsDTO newProduct(ProductsDTO productsDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = (authentication != null) ? authentication.getName() : "UNKNOWN";
        log.info("Creating new product with name: {}", productsDTO.getProductName());
        Products products = Products.builder()
                .productName(productsDTO.getProductName())
                .createdBy(fullName)
                .createdOn(LocalDateTime.now())
                .build();
        products = productsRepository.save(products);
        log.info("Product created successfully with id: {}", products.getId());
        return ProductsDTO.fromEntity(products);
    }

    @Override
    public Page<ProductsDTO> getAllProducts(int page, int size) {
        log.info("Fetching products page: {}, size: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Products> allProducts = productsRepository.findAll(pageable);
        return allProducts.map(ProductsDTO::fromEntity);
    }

    @Override
    public Optional<ProductsDTO> getProduct(Long id) {
        log.info("Fetching product with id: {}", id);
        Optional<Products> product = productsRepository.findById(id);
        if (product.isEmpty()) {
            log.warn("Product not found with id: {}", id);
        }
        return product.map(ProductsDTO::fromEntity);
    }

    @Override
    public ProductsDTO updateProduct(Long id, ProductsDTO productsDTO) {


        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String fullName = (authentication != null) ? authentication.getName() : "UNKNOWN";
        log.info("Updating product with id: {}", id);
        Products existingProduct = productsRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Product not found for update with id: {}", id);
                    return new ResourceNotFound("Product Not Found With Id: " + id);
                });
        existingProduct.setProductName(productsDTO.getProductName());
       existingProduct.setModifiedBy(fullName);
       existingProduct.setModifiedOn(LocalDateTime.now());
        Products updatedProduct = productsRepository.save(existingProduct);
        log.info("Product updated successfully with id: {}", id);
        return ProductsDTO.fromEntity(updatedProduct);
    }

    @Override
    public Boolean deleteProduct(Long id) {
        log.info("Deleting product with id: {}", id);
        if (!productsRepository.existsById(id)) {
            log.warn("Product not found for deletion with id: {}", id);
            return false;
        }
        productsRepository.deleteById(id);
        log.info("Product deleted successfully with id: {}", id);
        return true;
    }

    @Override
    public List<ItemsDTO> allItemsById(Long id) {
        log.info("Fetching items for product id: {}", id);
        List<Items> allItems = itemsRepository.findItemsByProductId(id);
        if (allItems.isEmpty()) {
            log.warn("No items found for product id: {}", id);
            throw new ResourceNotFound("Items Not Found With Product Id: " + id);
        }
        return allItems.stream()
                .map(ItemsDTO::fromEntity)
                .collect(Collectors.toList());
    }


}
