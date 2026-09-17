package com.m604.inventorymanagement.Service;

import com.m604.inventorymanagement.Repository.CategoryRepository;
import com.m604.inventorymanagement.Repository.ProductRepository;
import com.m604.inventorymanagement.Repository.StockTransactionRepository;
import com.m604.inventorymanagement.Repository.SupplierRepository;
import com.m604.inventorymanagement.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.m604.inventorymanagement.exception.InvalidStockQuantityException;
import com.m604.inventorymanagement.exception.InsufficientStockException;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.m604.inventorymanagement.entity.Product;
import com.m604.inventorymanagement.entity.StockTransaction;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockTransactionRepository stockTransactionRepository;

    @Mock
    private SupplierRepository supplierRepository;

    @Mock
    private CategoryRepository categoryRepository;
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductServiceImpl(
                productRepository,
                stockTransactionRepository,
                supplierRepository,
                categoryRepository
        );
    }
    @Test
    void stockOutShouldReduceQuantity() {

        Product product = new Product();
        product.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.stockOut(
                1L,
                4,
                "Customer order"
        );

        assertEquals(6, result.getQuantity());

        verify(stockTransactionRepository)
                .save(any(StockTransaction.class));
    }
    @Test
    void stockOutShouldFailWhenStockIsInsufficient() {

        Product product = new Product();
        product.setQuantity(5);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                InsufficientStockException.class,
                () -> productService.stockOut(
                        1L,
                        10,
                        "Too large order"
                )
        );
    }
    @Test
    void stockOutShouldFailForNegativeQuantity() {

        Product product = new Product();
        product.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                InvalidStockQuantityException.class,
                () -> productService.stockOut(
                        1L,
                        -2,
                        "Invalid quantity"
                )
        );
    }

    @Test
    void stockInShouldIncreaseQuantity() {

        Product product = new Product();
        product.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.stockIn(
                1L,
                5,
                "New shipment"
        );

        assertEquals(15, result.getQuantity());

        verify(stockTransactionRepository)
                .save(any(StockTransaction.class));
    }
}