package com.sayan.ecommerceApp.order_service.repositories;

import com.sayan.ecommerceApp.order_service.entities.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Long> {
}
