package com.example.orderservice.service;


import com.example.orderservice.model.CustomerOrders;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface CustomerOrdersService {
    public List<CustomerOrders> getCustomerOrders();

    public boolean createCustomerOrder(CustomerOrders customerOrders) throws SQLException;

}
