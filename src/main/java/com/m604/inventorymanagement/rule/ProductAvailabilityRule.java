package com.m604.inventorymanagement.rule;

import com.m604.inventorymanagement.entity.Product;

public abstract class ProductAvailabilityRule {

    public abstract boolean isAvailable(Product product);
}