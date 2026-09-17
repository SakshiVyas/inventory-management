package com.m604.inventorymanagement.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Integer requested, Integer available) {
        super("Requested " + requested +
                " units, but only " + available + " are available");
    }
}