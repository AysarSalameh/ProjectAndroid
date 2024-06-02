package com.example.projectandroid;

import java.io.Serializable;

public class Car implements Serializable {
    private int carID;
    private int brandID;
    private String model;
    private int year;
    private String carClass;
    private int seats;
    private int doors;
    private String image;
    private double pricePerDay;
    private double rating;
    private String description;
    private String fuelConsumption;

    public Car(int carID, int brandID, String model, int year, String carClass, int seats, int doors, String image, double pricePerDay, double rating, String description, String fuelConsumption) {//for all data of the calr information view
        this.carID = carID;
        this.brandID = brandID;
        this.model = model;
        this.year = year;
        this.carClass = carClass;
        this.seats = seats;
        this.doors = doors;
        this.image = image;
        this.pricePerDay = pricePerDay;
        this.rating = rating;
        this.description = description;
        this.fuelConsumption = fuelConsumption;
    }
    public Car(int carID, int brandID, String model, int year, String carClass, int seats, int doors, String image, double pricePerDay, double rating) {//for the car card data
        this.carID = carID;
        this.brandID = brandID;
        this.model = model;
        this.year = year;
        this.carClass = carClass;
        this.seats = seats;
        this.doors = doors;
        this.image = image;
        this.pricePerDay = pricePerDay;
        this.rating = rating;

    }

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCarClass() {
        return carClass;
    }

    public void setCarClass(String carClass) {
        this.carClass = carClass;
    }

    public int getSeats() {
        return seats;
    }

    public void setSeats(int seats) {
        this.seats = seats;
    }

    public int getDoors() {
        return doors;
    }

    public void setDoors(int doors) {
        this.doors = doors;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(String fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }
}
