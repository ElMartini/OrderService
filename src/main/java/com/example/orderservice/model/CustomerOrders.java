package com.example.orderservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerOrders implements Serializable {
    @Id
    @JsonProperty("oNumber")
    private int oNumber;

    @JsonProperty("cID")
    private String cID;

    @JsonProperty("oIDs")
    private String oIDs;

    @JsonProperty("oValue")
    private double oValue;

    public int getoNumber() {
        return oNumber;
    }

    public void setoNumber(int oNumber) {
        this.oNumber = oNumber;
    }

    public String getcID() {
        return cID;
    }

    public void setcID(String cID) {
        this.cID = cID;
    }

    public String getoIDs() {
        return oIDs;
    }

    public void setoIDs(String oIDs) {
        this.oIDs = oIDs;
    }

    public double getoValue() {
        return oValue;
    }

    public void setoValue(double oValue) {
        this.oValue = oValue;
    }
}
