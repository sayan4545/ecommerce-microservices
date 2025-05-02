package com.sayan.ecommerceApp.inventory_service.service;

import com.sayan.ecommerceApp.inventory_service.dto.OrderRequestDto;
import com.sayan.ecommerceApp.inventory_service.dto.OrderRequestItemDto;
import com.sayan.ecommerceApp.inventory_service.dto.ProductDto;
import com.sayan.ecommerceApp.inventory_service.entities.Product;
import com.sayan.ecommerceApp.inventory_service.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelmapper;

    public List<ProductDto> getAllInventory(){
        log.info("fetching all inventory items..");
        List<Product> inventories = productRepository.findAll();
        return inventories.stream()
                .map(product->modelmapper.map(product,ProductDto.class))
                .toList();

    }

    public ProductDto getProductById(Long id){
        log.info("Fetching product with ID: {}",id);
        Optional<Product> inventory = productRepository.findById(id);
        return inventory.map(item ->modelmapper.map(item,ProductDto.class))
                .orElseThrow(()-> new RuntimeException("Inventory not found"));
    }

    @Transactional
    public Double reduceStocks(OrderRequestDto orderRequestDto) {
        log.info("Reducing stocks..");
        Double totalPrice = 0.0;
        for(OrderRequestItemDto orderRequestItemDto : orderRequestDto.getItems()){
            Long productId = orderRequestItemDto.getProductId();
            Integer quantity = orderRequestItemDto.getQuantity();
            Product product = productRepository.findById(productId)
                    .orElseThrow(()-> new RuntimeException("Product not fopund"));
            if(product.getStock()< quantity){
                throw new RuntimeException("Exceeds quantity");
            }
            productRepository.save(product);
            product.setStock(product.getStock()-quantity);
            totalPrice = totalPrice + quantity*product.getPrice();
        }
        return totalPrice;
    }
}
