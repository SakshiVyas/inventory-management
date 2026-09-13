package com.m604.inventorymanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound(
            ProductNotFoundException ex) {

        Map<String, Object> body = Map.of(
                "status", 404,
                "error", "NOT_FOUND",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }
    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<Map<String, Object>> handleInsufficientStock(
            InsufficientStockException ex) {

        Map<String, Object> body = Map.of(
                "status", 400,
                "error", "INSUFFICIENT_STOCK",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(InvalidStockQuantityException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidStockQuantity(
            InvalidStockQuantityException ex) {

        Map<String, Object> body = Map.of(
                "status", 400,
                "error", "INVALID_STOCK_QUANTITY",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(SupplierNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSupplierNotFound(
            SupplierNotFoundException ex) {

        Map<String, Object> body = Map.of(
                "status", 404,
                "error", "NOT_FOUND",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(body);
    }
    @ExceptionHandler(DuplicateSkuException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateSku(
            DuplicateSkuException ex) {

        Map<String, Object> body = Map.of(
                "status", 409,
                "error", "DUPLICATE_SKU",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        Map<String, String> validationErrors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        validationErrors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        Map<String, Object> body = Map.of(
                "status", 400,
                "error", "VALIDATION_FAILED",
                "details", validationErrors
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(body);
    }

    @ExceptionHandler(DuplicateSupplierEmailException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateSupplierEmail(
            DuplicateSupplierEmailException ex) {

        Map<String, Object> body = Map.of(
                "status", 409,
                "error", "DUPLICATE_SUPPLIER_EMAIL",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
    @ExceptionHandler(SupplierInUseException.class)
    public ResponseEntity<Map<String, Object>> handleSupplierInUse(
            SupplierInUseException ex) {

        Map<String, Object> body = Map.of(
                "status", 409,
                "error", "SUPPLIER_IN_USE",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
    @ExceptionHandler(ProductInUseException.class)
    public ResponseEntity<Map<String, Object>> handleProductInUse(
            ProductInUseException ex) {

        Map<String, Object> body = Map.of(
                "status", 409,
                "error", "PRODUCT_IN_USE",
                "message", ex.getMessage()
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
    @ExceptionHandler(ObjectOptimisticLockingFailureException.class)
    public ResponseEntity<Map<String, Object>> handleOptimisticLockConflict(
            ObjectOptimisticLockingFailureException ex) {

        Map<String, Object> body = Map.of(
                "status", 409,
                "error", "CONCURRENT_UPDATE",
                "message", "The product was updated by another request. Please try again."
        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(body);
    }
}