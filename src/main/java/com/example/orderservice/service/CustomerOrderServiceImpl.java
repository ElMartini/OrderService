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

@Service
public class CustomerOrderServiceImpl implements CustomerOrdersService {

    private Connection connection;
    private List<CustomerOrders> customerOrdersList = new ArrayList<>();

    private final OrderServiceImpl orderService = new OrderServiceImpl();

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
                customerOrders.setoNumber(result.getInt(1));
                customerOrders.setcID(result.getString(2));
                customerOrders.setoIDs(result.getString(3));
                customerOrders.setoValue(result.getDouble(4));
                customerOrdersList.add(customerOrders);
            }

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return customerOrdersList;
    }

    public boolean createCustomerOrder(CustomerOrders customerOrders) throws SQLException {
        String statementQuery = "INSERT INTO customerOrders (oNumber, cID, oIDs, oValue) VALUES (?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(statementQuery);
        System.out.println(customerOrders.getoIDs());

        statement.setInt(1, generateEightDigitalNumber());
        statement.setString(2, customerOrders.getcID());
        statement.setString(3, customerOrders.getoIDs());
        statement.setDouble(4, sumValue(customerOrders.getoIDs()));


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
        return randomNumber;
    }

    private boolean isOrderNumberAvailabe(int oNumber) {
        for (CustomerOrders customerOrders : customerOrdersList) {
            if (oNumber == customerOrders.getoNumber()) return false;
        }
        return true;
    }

    private double sumValue(String oIDsString) {
        System.out.println(oIDsString);
        if (oIDsString == null) {
            return 0.0;
        }
        double sum = 0;
        List<String> oIDs= List.of(oIDsString.split(", "));

        for (String oID : oIDs) {
            Order order = orderService.getOrder(oID);
            sum+=order.getoPrice();
        }

        return sum;
    }

    public CustomerOrders getCustomerOrder(int oNumber){
        List<CustomerOrders> list =getCustomerOrders();
        for(CustomerOrders customerOrders: list){
            if(customerOrders.getoNumber()==oNumber){
                return customerOrders;
            }
        }
        return null;
    }


}
