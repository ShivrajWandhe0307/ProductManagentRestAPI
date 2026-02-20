package com.example.ProductManagementRestAPI.Controller;


import com.example.ProductManagementRestAPI.DTO.ItemsDTO;
import com.example.ProductManagementRestAPI.Service.ItemsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/items")
public class ItemsController
{

    private final ItemsService itemsService;
    @PostMapping
    public ResponseEntity<ItemsDTO> newItems(@RequestBody ItemsDTO itemsDTO) {

        log.info("Received request to create new item: {}", itemsDTO);

        ItemsDTO items = itemsService.newItems(itemsDTO);

        log.info("Item created successfully with data: {}", items);

        return new ResponseEntity<>(items, HttpStatus.CREATED);
    }

}
