package com.example.orderservice.service;

import com.example.orderservice.DBUtil.DBUtil;
import com.example.orderservice.model.Order;
import org.aspectj.weaver.ast.Or;
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
                order.setoID(result.getString(1));
                order.setpName(result.getString(2));
                order.setpPrice(result.getDouble(3));
                order.setpQuantity(result.getInt(4));
                order.setoPrice(result.getDouble(5));
                orderList.add(order);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return orderList;
    }

    public boolean placeOrder(Order order) throws SQLException {
        String statementQuery = "INSERT INTO orders (oID, pName, pPrice, pQuantity, oPrice) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(statementQuery);

        if (order.getoID() == null || order.getoID().isEmpty()) {
            order.setoID(UUID.randomUUID().toString());
        }

        statement.setString(1, order.getoID());
        statement.setString(2, order.getpName());
        statement.setDouble(3, order.getpPrice());
        statement.setInt(4, order.getpQuantity());
        statement.setDouble(5, order.getoPrice());


        int result = statement.executeUpdate();
        return result > 0;
    }

    public Order getOrder(String oID){
        for (Order order: getOrders()){
            if(order.getoID().equals(oID)) return order;
        }
        return null;
    }
}
