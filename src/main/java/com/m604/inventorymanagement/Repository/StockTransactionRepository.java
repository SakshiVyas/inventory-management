package com.m604.inventorymanagement.Repository;

import com.m604.inventorymanagement.entity.StockTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StockTransactionRepository
        extends JpaRepository<StockTransaction, Long> {

    List<StockTransaction> findByProductId(Long productId);
    boolean existsByProductId(Long productId);
}