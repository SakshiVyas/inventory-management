package com.m604.inventorymanagement.rule;

import com.m604.inventorymanagement.entity.Product;
import org.springframework.stereotype.Component;

@Component
public class StandardProductAvailabilityRule
        extends ProductAvailabilityRule {

    @Override
    public boolean isAvailable(Product product) {
        return product.getQuantity() > 0;
    }
}