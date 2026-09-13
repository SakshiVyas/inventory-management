package com.m604.inventorymanagement.exception;

public class ProductInUseException extends RuntimeException {

    public ProductInUseException(Long id) {
        super("Product with id " + id +
                " cannot be deleted because stock transactions exist");
    }
}