package com.example.orderservice.action;

import com.example.orderservice.kafka.KafkaProducer;
import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.OrderServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CustomerOrderToOrderList {

    OrderServiceImpl orderService = new OrderServiceImpl();
    private final KafkaProducer kafkaProducer;



    public CustomerOrderToOrderList(KafkaProducer kafkaProducer) throws SQLException, ClassNotFoundException {
        this.kafkaProducer = kafkaProducer;
    }

    public void getOrderListFromCustomerOrder(CustomerOrders customerOrders) {
        List<Order> list = new ArrayList<>();
        List<String> ordersIDs = customerOrderOIDsToList(customerOrders.getoIDs());
        for (String s : ordersIDs) {
            Order order = orderService.getOrder(s);
            list.add(order);
        }
        System.out.println("OrderList: " + list);

        kafkaProducer.sendOrderList(list);
    }

    List<String> customerOrderOIDsToList(String oIDs) {
        List<String> oIDsList = Arrays.stream(oIDs.split(", ")).toList();
        System.out.println("List: " + oIDsList);
        return oIDsList;
    }
}
