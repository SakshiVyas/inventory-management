package com.m604.inventorymanagement.service;

import com.m604.inventorymanagement.Repository.CategoryRepository;
import com.m604.inventorymanagement.entity.*;
import com.m604.inventorymanagement.Repository.ProductRepository;
import com.m604.inventorymanagement.exception.*;

import org.springframework.stereotype.Service;
import com.m604.inventorymanagement.Repository.StockTransactionRepository;
import com.m604.inventorymanagement.Repository.SupplierRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final StockTransactionRepository stockTransactionRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public ProductServiceImpl(
            ProductRepository productRepository,
            StockTransactionRepository stockTransactionRepository,
            SupplierRepository supplierRepository,
            CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.stockTransactionRepository = stockTransactionRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Product createProduct(Product product) {

        productRepository.findBySku(product.getSku())
                .ifPresent(existingProduct -> {
                    throw new DuplicateSkuException(product.getSku());
                });

        Long supplierId = product.getSupplier().getId();

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));
        Category categoryEntity = categoryRepository
                .findByName(product.getCategory())
                .orElseGet(() ->
                        categoryRepository.save(
                                new Category(product.getCategory())
                        )
                );
        product.setSupplier(supplier);
        product.setCategoryEntity(categoryEntity);

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    @Override
    public Product updateProduct(Long id, Product updatedProduct) {

        Product product = getProductById(id);

        productRepository.findBySku(updatedProduct.getSku())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateSkuException(updatedProduct.getSku());
                });

        Long supplierId = updatedProduct.getSupplier().getId();

        Supplier supplier = supplierRepository.findById(supplierId)
                .orElseThrow(() -> new SupplierNotFoundException(supplierId));
        Category categoryEntity = categoryRepository
                .findByName(updatedProduct.getCategory())
                .orElseGet(() ->
                        categoryRepository.save(
                                new Category(updatedProduct.getCategory())
                        )
                );
        product.setSku(updatedProduct.getSku());
        product.setName(updatedProduct.getName());
        product.setCategory(updatedProduct.getCategory());
        product.setPrice(updatedProduct.getPrice());
        product.setReorderLevel(updatedProduct.getReorderLevel());
        product.setExpirationDate(updatedProduct.getExpirationDate());
        product.setSupplier(supplier);
        product.setCategoryEntity(categoryEntity);

        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {

        Product product = getProductById(id);

        if (stockTransactionRepository.existsByProductId(id)) {
            throw new ProductInUseException(id);
        }

        productRepository.delete(product);
    }

    @Override
    public List<Product> getLowStockProducts() {
        return productRepository.findLowStockProducts();
    }
    @Transactional
    @Override
    public Product stockIn(Long productId, Integer quantity, String note) {
        if (quantity == null || quantity <= 0) {
            throw new InvalidStockQuantityException(quantity);
        }

        Product product = getProductById(productId);

        product.setQuantity(product.getQuantity() + quantity);

        StockTransaction transaction = new StockTransaction();
        transaction.setProduct(product);
        transaction.setType(TransactionType.STOCK_IN);
        transaction.setQuantity(quantity);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setNote(note);

        productRepository.save(product);
        stockTransactionRepository.save(transaction);

        return product;
    }
    @Transactional
    @Override
    public Product stockOut(Long productId, Integer quantity, String note) {
        if (quantity == null || quantity <= 0) {
            throw new InvalidStockQuantityException(quantity);
        }

        Product product = getProductById(productId);

        if (quantity > product.getQuantity()) {
            throw new InsufficientStockException(
                    quantity,
                    product.getQuantity()
            );
        }

        product.setQuantity(product.getQuantity() - quantity);

        StockTransaction transaction = new StockTransaction();
        transaction.setProduct(product);
        transaction.setType(TransactionType.STOCK_OUT);
        transaction.setQuantity(quantity);
        transaction.setTimestamp(LocalDateTime.now());
        transaction.setNote(note);

        productRepository.save(product);
        stockTransactionRepository.save(transaction);

        return product;
    }
    @Override
    public List<StockTransaction> getTransactionHistory(Long productId) {

        getProductById(productId); // ensures the product exists

        return stockTransactionRepository.findByProductId(productId);
    }
}