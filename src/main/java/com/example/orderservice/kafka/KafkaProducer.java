package com.example.orderservice.kafka;

import com.example.orderservice.model.CustomerOrders;
import com.example.orderservice.model.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class KafkaProducer {

    @Autowired
    private KafkaTemplate <String,Object> kafkaTemplate;

    KafkaProducer(KafkaTemplate<String, Object> kafkaTemplate){
        this.kafkaTemplate=kafkaTemplate;
    }

    public void sendOrderList(List<Order> orderList){
        kafkaTemplate.send("orderMainOrdersToListResponse", orderList);
    }
    public void getCustomerByONumberespone(CustomerOrders customerOrders){
        System.out.println("getCustomerByONumberespone1");
        kafkaTemplate.send("orderMainCustomerOrdersRespone" ,customerOrders);
        System.out.println("getCustomerByONumberespone2");
    }

    public void test1(){
        kafkaTemplate.send("test1", "hmm");
    }
    public void test2(){
        kafkaTemplate.send("test2", "hmm");
    }

}
