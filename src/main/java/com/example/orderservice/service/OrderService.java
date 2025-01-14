package com.example.orderservice.service;


import com.example.orderservice.model.Order;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface OrderService {
    public List<Order> getOrders();

    public boolean placeOrder(Order order) throws SQLException;

}
