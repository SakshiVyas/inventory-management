package com.m604.inventorymanagement.Repository;

import com.m604.inventorymanagement.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findBySku(String sku);
    Optional<Product> findByExternalId(String externalId);

    List<Product> findByQuantityLessThanEqual(Integer quantity);
    boolean existsBySupplierId(Long supplierId);
    @Query("SELECT p FROM Product p WHERE p.quantity <= p.reorderLevel")
    List<Product> findLowStockProducts();
}