package com.m604.inventorymanagement.exception;

public class DuplicateSupplierEmailException extends RuntimeException {

    public DuplicateSupplierEmailException(String email) {
        super("A supplier with email '" + email + "' already exists");
    }
}