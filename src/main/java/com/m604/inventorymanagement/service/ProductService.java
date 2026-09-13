package com.m604.inventorymanagement.service;

import com.m604.inventorymanagement.entity.Product;
import com.m604.inventorymanagement.entity.StockTransaction;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);

    List<Product> getAllProducts();

    Product getProductById(Long id);

    Product updateProduct(Long id, Product product);

    void deleteProduct(Long id);

    List<Product> getLowStockProducts();

    Product stockIn(Long productId, Integer quantity, String note);

    Product stockOut(Long productId, Integer quantity, String note);

    List<StockTransaction> getTransactionHistory(Long productId);
}