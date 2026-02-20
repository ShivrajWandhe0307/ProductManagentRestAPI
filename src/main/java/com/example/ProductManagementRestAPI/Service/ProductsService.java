package com.example.ProductManagementRestAPI.Service;

import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import com.example.ProductManagementRestAPI.DTO.ProductsDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ProductsService {

    public ProductsDTO newProduct(ProductsDTO productsDTO);

    public Page<ProductsDTO> getAllProducts(int page,int size);

    public Optional<ProductsDTO> getProduct(Long id);

    public ProductsDTO updateProduct(Long id, ProductsDTO productsDTO);

    public Boolean deleteProduct(Long id);

    public List<ItemsDTO> allItemsById(Long id);
}
