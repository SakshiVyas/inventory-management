package com.m604.inventorymanagement.rule;

import com.m604.inventorymanagement.entity.Product;
import com.m604.inventorymanagement.rule.PerishableProductAvailabilityRule;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PerishableProductAvailabilityRuleTest {

    private final PerishableProductAvailabilityRule rule =
            new PerishableProductAvailabilityRule();

    @Test
    void nonExpiredProductShouldBeAvailable() {

        Product product = new Product();
        product.setQuantity(10);
        product.setExpirationDate(LocalDate.now().plusDays(5));

        assertTrue(rule.isAvailable(product));
    }

    @Test
    void expiredProductShouldNotBeAvailable() {

        Product product = new Product();
        product.setQuantity(10);
        product.setExpirationDate(LocalDate.now().minusDays(1));

        assertFalse(rule.isAvailable(product));
    }
}