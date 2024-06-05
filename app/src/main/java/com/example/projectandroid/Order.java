// Order.java
package com.example.projectandroid;

import java.io.Serializable;

public class Order implements Serializable {
    private int orderID;
    private int carID;
    private String fullName;
    private String email;
    private double totalCost;
    private String carImageUrl;
    private String orderStatus;

    public Order(int orderID, int carID, String fullName, String email, double totalCost, String carImageUrl, String orderStatus) {
        this.orderID = orderID;
        this.carID = carID;
        this.fullName = fullName;
        this.email = email;
        this.totalCost = totalCost;
        this.carImageUrl = carImageUrl;
        this.orderStatus = orderStatus;
    }

    // Getters
    public int getOrderID() {
        return orderID;
    }

    public int getCarID() {
        return carID;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public String getCarImageUrl() {
        return carImageUrl;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}
