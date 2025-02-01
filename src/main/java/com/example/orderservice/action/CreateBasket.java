package com.example.orderservice.action;

import com.example.orderservice.dto.BasketDTO;
import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.model.Order;
import com.example.orderservice.model.Product;
import com.example.orderservice.service.CustomerOrderServiceImpl;
import com.example.orderservice.service.OrderServiceImpl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CreateBasket {
    CustomerOrders customerOrders = new CustomerOrders();
    List<Order> orderBasket = new ArrayList<>();
    OrderServiceImpl orderService = new OrderServiceImpl();
    CustomerOrderServiceImpl customerOrderService = new CustomerOrderServiceImpl();

    public CreateBasket() throws SQLException, ClassNotFoundException {
    }

    public void addToBasket(BasketDTO basketDTO) throws SQLException {
        List<String> oIDs = new ArrayList<>();

        for (Product p : basketDTO.getProducts()) {
        String oID=createOrder(p);
        oIDs.add(oID);
        }

        String oID = String.join(", ", oIDs);
        customerOrders.setoIDs(oID);
        customerOrders.setcID(basketDTO.getcID());
        customerOrderService.createCustomerOrder(customerOrders);

    }

    private String createOrder(Product product) throws SQLException {
        Order order = new Order();

        if (order.getoID() == null || order.getoID().isEmpty()) {
            order.setoID(UUID.randomUUID().toString());
        }

        order.setpName(product.getpName());
        order.setpPrice(product.getpPrice());
        order.setpQuantity(product.getpQuantity());
        double oPrice = order.getpPrice() * order.getpQuantity();
        order.setoPrice(oPrice);


        orderService.placeOrder(order);
        return order.getoID();
    }

}
