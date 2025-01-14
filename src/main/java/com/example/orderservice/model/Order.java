package com.example.orderservice.model;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order {
    @Id
    private String oID;
    private String pName;
    private Double oPrice;
    private int oQuantity;

}
