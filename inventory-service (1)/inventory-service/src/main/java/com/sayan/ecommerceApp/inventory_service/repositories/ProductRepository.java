package com.sayan.ecommerceApp.inventory_service.repositories;

import com.sayan.ecommerceApp.inventory_service.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
}
