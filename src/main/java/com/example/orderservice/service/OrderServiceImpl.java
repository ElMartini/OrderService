package com.example.orderservice.service;

import com.example.orderservice.DBUtil.DBUtil;
import com.example.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private Connection connection;
    private List<Order> orderList = new ArrayList<>();

    public OrderServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBUtil.getConnection();
    }

    public List<Order> getOrders() {
        try {
            connection = DBUtil.getConnection();
            orderList.clear();

            String statementQuery = "SELECT * FROM orders";
            PreparedStatement statement = connection.prepareStatement(statementQuery);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                Order order = new Order();
                order.setOID(result.getString(1));
                order.setPName(result.getString(2));
                order.setOPrice(result.getDouble(3));
                order.setOQuantity(result.getInt(4));
                orderList.add(order);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    public boolean placeOrder(Order order) throws SQLException {
        String statementQuery = "INSERT INTO orders (oID, pName, oPrice, oQuantity) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(statementQuery);

        if (order.getOID() == null || order.getOID().isEmpty()) {
            order.setOID(UUID.randomUUID().toString());
        }

        statement.setString(1, order.getOID());
        statement.setString(2, order.getPName());
        statement.setDouble(3, order.getOPrice());
        statement.setInt(4, order.getOQuantity());

        int result = statement.executeUpdate();
        return result > 0;
    }
}
