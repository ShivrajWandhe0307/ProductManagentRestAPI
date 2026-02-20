package com.example.ProductManagementRestAPI.DTO;


import com.example.ProductManagementRestAPI.Entity.Items;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemsDTO {


    private Long id;

    private Integer quantity;

    private Long productId;


    public static ItemsDTO fromEntity(Items items)
    {
        return ItemsDTO.builder()
                .id(items.getId())
                .quantity(items.getQuantity())
                .productId(items.getProduct()!=null?items.getProduct().getId():null)
                .build();
    }
}
