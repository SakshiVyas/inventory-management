package com.m604.inventorymanagement.service;

import com.m604.inventorymanagement.entity.Supplier;
import com.m604.inventorymanagement.Repository.SupplierRepository;
import com.m604.inventorymanagement.exception.DuplicateSupplierEmailException;
import com.m604.inventorymanagement.exception.SupplierNotFoundException;
import org.springframework.stereotype.Service;
import com.m604.inventorymanagement.exception.SupplierInUseException;
import com.m604.inventorymanagement.Repository.ProductRepository;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final ProductRepository productRepository;

    public SupplierServiceImpl(
            SupplierRepository supplierRepository,
            ProductRepository productRepository) {

        this.supplierRepository = supplierRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Supplier createSupplier(Supplier supplier) {

        supplierRepository.findByEmail(supplier.getEmail())
                .ifPresent(existingSupplier -> {
                    throw new DuplicateSupplierEmailException(
                            supplier.getEmail()
                    );
                });

        return supplierRepository.save(supplier);
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    @Override
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException(id));
    }

    @Override
    public Supplier updateSupplier(Long id, Supplier updatedSupplier) {

        Supplier supplier = getSupplierById(id);

        supplierRepository.findByEmail(updatedSupplier.getEmail())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new DuplicateSupplierEmailException(
                            updatedSupplier.getEmail()
                    );
                });

        supplier.setName(updatedSupplier.getName());
        supplier.setEmail(updatedSupplier.getEmail());
        supplier.setPhone(updatedSupplier.getPhone());

        return supplierRepository.save(supplier);
    }

    @Override
    public void deleteSupplier(Long id) {

        Supplier supplier = getSupplierById(id);

        if (productRepository.existsBySupplierId(id)) {
            throw new SupplierInUseException(id);
        }

        supplierRepository.delete(supplier);
    }
}