package com.m604.inventorymanagement.exception;

public class SupplierInUseException extends RuntimeException {

    public SupplierInUseException(Long id) {
        super("Supplier with id " + id + " cannot be deleted because products are linked to it");
    }
}