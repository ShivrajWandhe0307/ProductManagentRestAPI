package com.example.ProductManagementRestAPI.Repository;

import com.example.ProductManagementRestAPI.Entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemsRepository extends JpaRepository<Items,Long> {

    List<Items> findItemsByProductId(Long id);
}
