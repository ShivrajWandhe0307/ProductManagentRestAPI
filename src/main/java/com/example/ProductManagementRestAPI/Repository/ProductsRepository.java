package com.example.ProductManagementRestAPI.Repository;

import com.example.ProductManagementRestAPI.Entity.Products;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductsRepository extends JpaRepository<Products, Long> {
}
