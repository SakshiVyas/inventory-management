package com.m604.inventorymanagement.controller;

import com.m604.inventorymanagement.service.CsvImportService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/import")
public class CsvImportController {

    private final CsvImportService csvImportService;

    public CsvImportController(CsvImportService csvImportService) {
        this.csvImportService = csvImportService;
    }

    @PostMapping("/products")
    public String importProducts() {

        int count = csvImportService.importProducts();

        return "CSV import completed. Products processed: " + count;
    }
}