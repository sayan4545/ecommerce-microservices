package com.sayan.ecommerceApp.inventory_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestItemDto {
    private Long productId;
    private Integer quantity;
}
