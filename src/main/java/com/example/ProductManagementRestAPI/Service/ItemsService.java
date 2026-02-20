package com.example.ProductManagementRestAPI.Service;

import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ItemsService {

    public ItemsDTO newItems(ItemsDTO itemsDTO);

    public Page<ItemsDTO> allItems(int page, int size);
}
