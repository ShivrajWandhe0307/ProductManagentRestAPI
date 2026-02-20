package com.example.ProductManagementRestAPI.Controller;


import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import com.example.ProductManagementRestAPI.DTO.ProductsDTO;
import com.example.ProductManagementRestAPI.Exception.ResourceNotFound;
import com.example.ProductManagementRestAPI.Service.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductsController {

    private final ProductsService productsService;

    @PostMapping
    public ResponseEntity<ProductsDTO> newProduct(@Valid @RequestBody ProductsDTO productsDTO) {

        log.info("Creating new product: {}", productsDTO);

        ProductsDTO product = productsService.newProduct(productsDTO);

        log.info("Product created successfully with id: {}", product.getId());

        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductsDTO> getProducts(@PathVariable Long id) {

        log.info("Fetching product with id: {}", id);

        ProductsDTO product = productsService.getProduct(id)
                .orElseThrow(() -> {
                    log.error("Product not found with id: {}", id);
                    return new ResourceNotFound("Product Not Found With Id: " + id);
                });

        return ResponseEntity.ok(product);
    }

    @GetMapping()
    public ResponseEntity<Page<ProductsDTO>> allProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        log.info("Request received to fetch all products - page: {}, size: {}", page, size);

        try {
            Page<ProductsDTO> products = productsService.getAllProducts(page, size);
            log.info("Fetched {} products on page {}", products.getNumberOfElements(), page);
            log.debug("Products data: {}", products.getContent());
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            log.error("Error fetching products for page {} and size {}: {}", page, size, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<ProductsDTO> updateProduct(@PathVariable Long id, @RequestBody ProductsDTO productsDTO) {

        log.info("Updating product with id: {}", id);

        ProductsDTO updatedProduct = productsService.updateProduct(id, productsDTO);

        log.info("Product updated successfully with id: {}", id);

        return ResponseEntity.ok(updatedProduct);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {

        log.info("Deleting product with id: {}", id);

        Boolean status = productsService.deleteProduct(id);

        if (status) {
            log.info("Product deleted successfully with id: {}", id);
            return ResponseEntity.ok("Product Deleted with Id: " + id);
        }

        log.warn("Attempted to delete non-existing product with id: {}", id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Product Not Found");
    }

    @GetMapping("/{id}/items")
    public ResponseEntity<List<ItemsDTO>> allItemsByProductId(@PathVariable Long id) {

        log.info("Fetching all items for product id: {}", id);

        List<ItemsDTO> allItems = productsService.allItemsById(id);
        log.info("Fetched all items for product id: {}", id);
        return ResponseEntity.ok(allItems);
    }
}
