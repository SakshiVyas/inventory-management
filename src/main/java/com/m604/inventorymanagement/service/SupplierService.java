package com.m604.inventorymanagement.service;

import com.m604.inventorymanagement.entity.Supplier;

import java.util.List;

public interface SupplierService {

    Supplier createSupplier(Supplier supplier);

    List<Supplier> getAllSuppliers();

    Supplier getSupplierById(Long id);

    Supplier updateSupplier(Long id, Supplier supplier);

    void deleteSupplier(Long id);
}