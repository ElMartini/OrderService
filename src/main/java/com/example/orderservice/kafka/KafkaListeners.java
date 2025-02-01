package com.example.orderservice.kafka;

import com.example.orderservice.action.CreateBasket;
import com.example.orderservice.action.CustomerOrderToOrderList;
import com.example.orderservice.dto.BasketDTO;
import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.model.Order;
import com.example.orderservice.service.CustomerOrderServiceImpl;
import com.example.orderservice.service.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.sql.SQLException;


@Service
public class KafkaListeners {

    private KafkaTemplate<String, Object> kafkaTemplate;
    ObjectMapper objectMapper = new ObjectMapper();
    OrderServiceImpl orderService = new OrderServiceImpl();
    CreateBasket createBasketClass = new CreateBasket();
    private KafkaProducer kafkaProducer;
    CustomerOrderToOrderList customerOrderToOrderList = new CustomerOrderToOrderList(kafkaProducer);
    CustomerOrderServiceImpl customerOrderService = new CustomerOrderServiceImpl();

    public KafkaListeners(KafkaProducer kafkaProducer, KafkaTemplate<String, Object> kafkaTemplate) throws SQLException, ClassNotFoundException {
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaProducer = kafkaProducer;
    }


    @KafkaListener(topics = "kafkaOrder", groupId = "orderServiceGroup")
    public void listen(ConsumerRecord<String, Object> data) throws JsonProcessingException, SQLException, ClassNotFoundException {

        OrderDTO orderDTO = objectMapper.readValue(data.value().toString(), OrderDTO.class);
        String action = orderDTO.getAction();
        Order order = orderDTO.getOrder();


        switch (action) {
            case "ADD" -> orderService.placeOrder(order);
        }

    }

    @KafkaListener(topics = "productOrderBasket", groupId = "productOrder")
    public void createBasket(ConsumerRecord<String, Object> data) throws JsonProcessingException, SQLException {
        BasketDTO basketDTO = objectMapper.readValue(data.value().toString(), BasketDTO.class);
        createBasketClass.addToBasket(basketDTO);
    }

    @KafkaListener(topics = "mainOrderOrdersToListRequest", groupId = "mainOrder")
    public void orderToList(ConsumerRecord<String, Object> data) throws JsonProcessingException {
        CustomerOrders customerOrders = objectMapper.readValue(data.value().toString(), CustomerOrders.class);
        customerOrderToOrderList.getOrderListFromCustomerOrder(customerOrders);
    }

    @KafkaListener(topics = "mainOrderCustomerOrdersRequest", groupId = "mainOrder")
    public void getCustomerByONumber(ConsumerRecord<String, Object> data) throws JsonProcessingException {
        System.out.println("getCustomerByONumber1");
        int oNumber = objectMapper.readValue(data.value().toString(), Integer.class);
        System.out.println("getCustomerByONumber2");

        CustomerOrders customerOrders =customerOrderService.getCustomerOrder(oNumber);
        System.out.println("getCustomerByONumber3");

        kafkaProducer.getCustomerByONumberespone(customerOrders);
        System.out.println("getCustomerByONumber4");

    }




}
