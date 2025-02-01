package com.example.orderservice.controllers;


import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.service.CustomerOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customerOrders")
public class CustomerOrdersController {

    @Autowired
    private CustomerOrdersService customerOrdersService;

    @GetMapping("/all")
    public ResponseEntity<List<CustomerOrders>> getCustomerOrders() {
        List<CustomerOrders> customerOrders = customerOrdersService.getCustomerOrders();
        return ResponseEntity.ok(customerOrders);
    }

    @PostMapping(value = "/add")
    public void addOrder(@RequestBody CustomerOrders customerOrders) throws InterruptedException {

        boolean isSuccess;
        do {
            try {
                System.out.println("1: "+customerOrders.getcID() +customerOrders.getoIDs());
                isSuccess = customerOrdersService.createCustomerOrder(customerOrders);
            } catch (SQLException e) {
                isSuccess = false;
            }
            if (!isSuccess)
                Thread.sleep(500);

        } while (!isSuccess);
    }
}
