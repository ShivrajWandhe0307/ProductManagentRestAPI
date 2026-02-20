package com.example.ProductManagementRestAPI.Entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Table(name = "product",
        indexes = @Index(name = "idx_product_name", columnList = "product_name"))
public class Products {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="product_name", nullable=false)
    private String productName;

    @Column(nullable=false)
    private String createdBy;

    @Column(nullable=false)
    private LocalDateTime createdOn;

    private String modifiedBy;

    @UpdateTimestamp
    private LocalDateTime modifiedOn;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Items> items;
}
