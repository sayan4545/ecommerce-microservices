package com.sayan.ecommerceApp.order_service.controller;
import com.sayan.ecommerceApp.order_service.clients.InventoryFeignClient;
import com.sayan.ecommerceApp.order_service.dtos.OrderRequestDto;
import com.sayan.ecommerceApp.order_service.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/core")
@RequiredArgsConstructor
@Slf4j
public class OrderController {
    private final OrderService orderService;
    private final InventoryFeignClient inventoryFeignClient;

    @GetMapping("/getOrders")
    public String getOrders(){
        return "From order service";
    }
    @PostMapping("/createOrder")
    public ResponseEntity<OrderRequestDto> createOrder(@RequestBody OrderRequestDto orderRequestDto){
        OrderRequestDto orderRequestDto1 = orderService.createOrder(orderRequestDto);
        return ResponseEntity.ok(orderRequestDto1);

    }

    @GetMapping
    public ResponseEntity<List<OrderRequestDto>> getAllOrders(HttpServletRequest httpServletRequest){
        log.info("Fetching all orders via controller");
        List<OrderRequestDto> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderRequestDto> getOrderById(@PathVariable Long id){
        log.info("Getting order by id..");
        OrderRequestDto order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }
}
