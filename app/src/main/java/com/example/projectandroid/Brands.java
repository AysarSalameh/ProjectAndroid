package com.example.projectandroid;

public class Brands {
    private String name_brand;
    private String logo;

    public Brands(String name_brand, String logo) {
        this.name_brand = name_brand;
        this.logo = logo;
    }

    public String getName_brand() {
        return name_brand;
    }

    public void setName_brand(String name_brand) {
        this.name_brand = name_brand;
    }

    public String getLogo() {
        return logo;
    }



    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return name_brand;

    }
}
