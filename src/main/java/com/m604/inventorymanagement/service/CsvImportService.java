package com.m604.inventorymanagement.service;

import com.m604.inventorymanagement.Repository.ProductRepository;
import com.m604.inventorymanagement.Repository.SupplierRepository;
import com.m604.inventorymanagement.entity.Category;
import org.springframework.stereotype.Service;

import org.springframework.core.io.ClassPathResource;
import com.m604.inventorymanagement.entity.Product;
import com.m604.inventorymanagement.entity.Supplier;

import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import com.m604.inventorymanagement.Repository.CategoryRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
public class CsvImportService {

    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;
    private final CategoryRepository categoryRepository;
    public CsvImportService(
            ProductRepository productRepository,
            SupplierRepository supplierRepository,
            CategoryRepository categoryRepository) {

        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;
        this.categoryRepository = categoryRepository;
    }
    public int importProducts() {

        try {
            ClassPathResource resource =
                    new ClassPathResource("data/products.csv");

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(resource.getInputStream())
                    );

            String line;

            // skip header
            reader.readLine();


            int importedCount = 0;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);

                String productId = values[0].trim();
                String productName = values[1].trim();
                String category = values[2].trim();
                Category categoryEntity = categoryRepository.findByName(category)
                        .orElseGet(() ->
                                categoryRepository.save(new Category(category))
                        );
                String supplierId = values[3].trim();
                String supplierName = values[4].trim();

                int stockQuantity = Integer.parseInt(values[5].trim());
                int reorderLevel = Integer.parseInt(values[6].trim());

                String priceText = values[8]
                        .replace("$", "")
                        .trim();

                String expirationText = values[11].trim();

                LocalDate expirationDate = expirationText.isBlank()
                        ? null
                        : LocalDate.parse(
                        expirationText,
                        DateTimeFormatter.ofPattern("M/d/yyyy")
                );

                Supplier supplier = supplierRepository.findByExternalId(supplierId)
                        .orElseGet(() -> {
                            Supplier newSupplier = new Supplier();
                            newSupplier.setExternalId(supplierId);
                            newSupplier.setName(supplierName);

                            // dataset has no supplier email, so create a unique placeholder
                            newSupplier.setEmail(
                                    supplierId.replace("-", "") + "@dataset.local"
                            );

                            return supplierRepository.save(newSupplier);
                        });
                Optional<Product> existingProduct =
                        productRepository.findByExternalId(productId);

                Product product = existingProduct.orElseGet(Product::new);

                product.setExternalId(productId);
                product.setSku(productId);
                product.setName(productName);
                product.setCategory(category);
                product.setCategoryEntity(categoryEntity);
                product.setPrice(new BigDecimal(priceText));

                if (existingProduct.isEmpty()) {
                    product.setQuantity(stockQuantity);
                }

                product.setReorderLevel(reorderLevel);
                product.setExpirationDate(expirationDate);
                product.setSupplier(supplier);

                productRepository.save(product);
                importedCount++;

                System.out.println(
                        productId + " | " +
                                productName + " | " +
                                supplierName + " | " +
                                stockQuantity + " | " +
                                priceText
                );
            }

            reader.close();

            return importedCount;
        } catch (Exception e) {
            throw new RuntimeException("Failed to import CSV", e);
        }
    }
}