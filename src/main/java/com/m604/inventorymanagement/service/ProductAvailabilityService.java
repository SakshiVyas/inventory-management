package com.m604.inventorymanagement.service;

import com.m604.inventorymanagement.entity.Product;
import com.m604.inventorymanagement.rule.PerishableProductAvailabilityRule;
import com.m604.inventorymanagement.rule.ProductAvailabilityRule;
import com.m604.inventorymanagement.rule.StandardProductAvailabilityRule;
import org.springframework.stereotype.Service;

@Service
public class ProductAvailabilityService {

    private final StandardProductAvailabilityRule standardRule;
    private final PerishableProductAvailabilityRule perishableRule;

    public ProductAvailabilityService(
            StandardProductAvailabilityRule standardRule,
            PerishableProductAvailabilityRule perishableRule) {

        this.standardRule = standardRule;
        this.perishableRule = perishableRule;
    }

    public boolean isAvailable(Product product) {

        ProductAvailabilityRule rule;

        if (product.getExpirationDate() != null) {
            rule = perishableRule;
        } else {
            rule = standardRule;
        }

        return rule.isAvailable(product);
    }
}