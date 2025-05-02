package com.sayan.ecommerceApp.inventory_service.controller;


import com.sayan.ecommerceApp.inventory_service.clients.OrdersFeignClient;
import com.sayan.ecommerceApp.inventory_service.dto.OrderRequestDto;
import com.sayan.ecommerceApp.inventory_service.dto.OrderRequestItemDto;
import com.sayan.ecommerceApp.inventory_service.dto.ProductDto;
import com.sayan.ecommerceApp.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.elasticsearch.ElasticsearchProperties;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final DiscoveryClient discoveryClient;
    private final RestClient restClient;
    private final OrdersFeignClient ordersFeignClient;

    @GetMapping("/fetchOrder")
    public String fetchOrder(){
//        ServiceInstance service = discoveryClient.getInstances("order-service")
//                .getFirst();
//        return restClient.get()
//                .uri(service.getUri()+"/orders/core/getOrders")
//                .retrieve()
//                .body(String.class);
        return ordersFeignClient.helloOrders();
    }
    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDto orderRequestDto){
        Double totalPrice = productService.reduceStocks(orderRequestDto);
        return ResponseEntity.ok(totalPrice);
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllInventories(){
        List<ProductDto> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getInventoryById(@PathVariable Long id){
        ProductDto inventory = productService.getProductById(id);
        return ResponseEntity.ok(inventory);
    }
}
