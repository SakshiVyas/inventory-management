package com.m604.inventorymanagement.rule;

import com.m604.inventorymanagement.entity.Product;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class PerishableProductAvailabilityRule
        extends ProductAvailabilityRule {

    @Override
    public boolean isAvailable(Product product) {

        if (product.getExpirationDate() == null) {
            return product.getQuantity() > 0;
        }

        return product.getQuantity() > 0
                && !product.getExpirationDate().isBefore(LocalDate.now());
    }
}