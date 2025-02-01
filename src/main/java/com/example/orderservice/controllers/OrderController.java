package com.example.orderservice.controllers;


import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/all")
    public ResponseEntity<List<Order>> getOrder() {
        List<Order> orders = orderService.getOrders();
        return ResponseEntity.ok(orders);
    }

    @PostMapping(value = "/add")
    public void addOrder(@RequestBody Order order) throws InterruptedException {

        boolean isSuccess;
        do {
            try {
                isSuccess = orderService.placeOrder(order);
            } catch (SQLException e) {
                isSuccess = false;
            }
            if (!isSuccess)
                Thread.sleep(500);

        } while (!isSuccess);
        }
}
