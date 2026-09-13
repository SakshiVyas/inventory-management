package com.m604.inventorymanagement.controller;

import com.m604.inventorymanagement.entity.Product;
import com.m604.inventorymanagement.entity.StockTransaction;
import com.m604.inventorymanagement.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.m604.inventorymanagement.dto.StockRequest;
import jakarta.validation.Valid;
import java.util.List;
import com.m604.inventorymanagement.service.ProductAvailabilityService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final ProductAvailabilityService productAvailabilityService;

    public ProductController(
            ProductService productService,
            ProductAvailabilityService productAvailabilityService) {

        this.productService = productService;
        this.productAvailabilityService = productAvailabilityService;
    }

    @GetMapping("/{id}/availability")
    public boolean isProductAvailable(@PathVariable Long id) {

        Product product = productService.getProductById(id);

        return productAvailabilityService.isAvailable(product);
    }
    @PostMapping("/{id}/stock-in")
    public Product stockIn(
            @PathVariable Long id,
            @Valid @RequestBody StockRequest request) {

        return productService.stockIn(
                id,
                request.getQuantity(),
                request.getNote()
        );
    }
    @PostMapping("/{id}/stock-out")
    public Product stockOut(
            @PathVariable Long id,
            @Valid @RequestBody StockRequest request) {

        return productService.stockOut(
                id,
                request.getQuantity(),
                request.getNote()
        );
    }
    @GetMapping("/{id}/transactions")
    public List<StockTransaction> getTransactionHistory(
            @PathVariable Long id) {

        return productService.getTransactionHistory(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product createProduct(@Valid @RequestBody Product product){
        return productService.createProduct(product);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @PutMapping("/{id}")
    public Product updateProduct(
            @PathVariable Long id,
            @Valid @RequestBody Product product) {

        return productService.updateProduct(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }

    @GetMapping("/low-stock")
    public List<Product> getLowStockProducts() {
        return productService.getLowStockProducts();
    }
}