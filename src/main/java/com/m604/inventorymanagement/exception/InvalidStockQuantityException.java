package com.m604.inventorymanagement.exception;

public class InvalidStockQuantityException extends RuntimeException {

    public InvalidStockQuantityException(Integer quantity) {
        super("Stock quantity must be greater than 0. Received: " + quantity);
    }
}