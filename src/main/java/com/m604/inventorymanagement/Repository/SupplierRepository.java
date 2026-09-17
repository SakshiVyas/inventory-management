package com.m604.inventorymanagement.Repository;

import com.m604.inventorymanagement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    Optional<Supplier> findByEmail(String email);
    Optional<Supplier> findByExternalId(String externalId);
}