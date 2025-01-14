package com.example.orderservice.controllers;


import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.service.CustomerOrdersService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/customerOrders")
public class CustomerOrdersController {

    @Autowired
    private CustomerOrdersService customerOrdersService;

    @GetMapping("/get")
    public List<CustomerOrders> getOrders() {
        return this.customerOrdersService.getCustomerOrders();
    }

    @PostMapping(value = "/add")
    public void addProduct(@RequestBody CustomerOrders customerOrders) throws InterruptedException {

        boolean isSuccess;
        do {
            try {
                isSuccess = customerOrdersService.createCustomerOrder(customerOrders);
            } catch (SQLException e) {
                isSuccess = false;
                log.info(e.toString());
            }
            if (!isSuccess)
                Thread.sleep(500);

        } while (!isSuccess);
        log.info("Success");
    }
}
