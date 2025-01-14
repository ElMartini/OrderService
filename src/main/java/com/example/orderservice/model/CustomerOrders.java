package com.example.orderservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CustomerOrders {
    @Id
    private int oNumber;
    private String cID;
    private String oIDs;
}
