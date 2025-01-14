package com.example.orderservice.service;

import com.example.orderservice.DBUtil.DBUtil;
import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.model.Order;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class CustomerOrderServiceImpl  implements CustomerOrdersService{

    private Connection connection;
    private List<CustomerOrders>customerOrdersList = new ArrayList<>();

    public CustomerOrderServiceImpl() throws SQLException, ClassNotFoundException {
        connection = DBUtil.getConnection();
    }

    public List<CustomerOrders> getCustomerOrders() {
        try {
            connection = DBUtil.getConnection();
            customerOrdersList.clear();

            String statementQuery = "SELECT * FROM customerOrders";
            PreparedStatement statement = connection.prepareStatement(statementQuery);
            ResultSet result = statement.executeQuery();

            while (result.next()) {
                CustomerOrders customerOrders = new CustomerOrders();
                customerOrders.setONumber(result.getInt(1));
                customerOrders.setCID(result.getString(2));
                customerOrders.setOIDs(result.getString(3));
                customerOrdersList.add(customerOrders);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return customerOrdersList;
    }

    public boolean createCustomerOrder(CustomerOrders customerOrders) throws SQLException {
        String statementQuery = "INSERT INTO customerOrders (oNumber, cID, oIDs) VALUES (?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(statementQuery);


        statement.setInt(1, generateEightDigitalNumber());
        statement.setString(2, customerOrders.getCID());
        statement.setString(3, customerOrders.getOIDs());


        int result = statement.executeUpdate();
        return result > 0;
    }

    private int generateEightDigitalNumber() {
        Random random = new Random();
        boolean isAvailabe;
        int randomNumber;
        do {
            randomNumber = 100_000_00 + random.nextInt(900_000_00);
            isAvailabe = isOrderNumberAvailabe(randomNumber);
        } while (!isAvailabe);
        System.out.println(randomNumber);
        return randomNumber;
    }

    private boolean isOrderNumberAvailabe(int oNumber) {
        for (CustomerOrders customerOrders: customerOrdersList) {
            if (oNumber == customerOrders.getONumber()) return false;
        }
        return true;
    }

}
