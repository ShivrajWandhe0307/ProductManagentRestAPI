package com.example.ProductManagementRestAPI.DTO;

import com.example.ProductManagementRestAPI.Entity.Products;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductsDTO {


    private Long id;

    @NotBlank(message = "Product name is required")
    @Size(min = 2, max = 50, message = "Product name must be between 2 and 50 characters")
    private String productName;

    private String createdBy;

    private LocalDateTime createdOn;

    private String modifiedBy;

    private LocalDateTime modifiedOn;



    public static ProductsDTO fromEntity(Products products)
    {
        return ProductsDTO.builder()
                .id(products.getId())
                .productName(products.getProductName())
                .createdBy(products.getCreatedBy())
                .createdOn(products.getCreatedOn())
                .modifiedBy(products.getModifiedBy())
                .modifiedOn(products.getModifiedOn())
                .build();
    }
}
