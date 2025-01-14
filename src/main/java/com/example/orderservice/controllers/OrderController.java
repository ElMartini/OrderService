package com.example.orderservice.controllers;


import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/get")
    public List<Order> getOrders() {
        return this.orderService.getOrders();
    }

    @PostMapping(value = "/add")
    public void addOrder(@RequestBody Order order) throws InterruptedException {

        boolean isSuccess;
        do {
            try {
                isSuccess = orderService.placeOrder(order);
            } catch (SQLException e) {
                isSuccess = false;
                log.info(e.toString());
            }
            if (!isSuccess)
                Thread.sleep(500);

        } while (!isSuccess);
        log.info("Success10");
    }
}
