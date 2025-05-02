package com.sayan.ecommerceApp.order_service.service;

import com.sayan.ecommerceApp.order_service.clients.InventoryFeignClient;
import com.sayan.ecommerceApp.order_service.dtos.OrderRequestDto;
import com.sayan.ecommerceApp.order_service.entities.OrderItem;
import com.sayan.ecommerceApp.order_service.entities.Orders;
import com.sayan.ecommerceApp.order_service.entities.enums.OrderStatus;
import com.sayan.ecommerceApp.order_service.repositories.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final ModelMapper modelMapper;
    private final OrdersRepository ordersRepository;
    private final InventoryFeignClient inventoryFeignClient;


    public List<OrderRequestDto> getAllOrders(){
        log.info("fetching all orders..");
        List<Orders> orders = ordersRepository.findAll();
        return orders.stream().map(order->modelMapper.map(order,OrderRequestDto.class))
                .toList();
    }

    public OrderRequestDto getOrderById(Long id){
        log.info("Fetching order with id : {}",id);
        Orders order = ordersRepository.findById(id).orElseThrow(()->new RuntimeException("Order not found.."));
        return modelMapper.map(order,OrderRequestDto.class);
    }
    public OrderRequestDto createOrder(OrderRequestDto orderRequestDto){
        Double totalPrice = inventoryFeignClient.reduceStocks(orderRequestDto);
        Orders orders = modelMapper.map(orderRequestDto,Orders.class);
        for(OrderItem orderItem:orders.getItems()){
            orderItem.setOrder(orders);

        }
        orders.setTotalPrice(totalPrice);
        orders.setOrderStatus(OrderStatus.CONFIRMED);
        Orders savedorders = ordersRepository.save(orders);
        return modelMapper.map(savedorders,OrderRequestDto.class);
    }

}
