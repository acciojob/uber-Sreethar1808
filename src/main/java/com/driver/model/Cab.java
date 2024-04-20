package com.driver.model;

import javax.persistence.*;

@Entity
public class Cab
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCabNo() {
        return cabNo;
    }

    public void setCabNo(String cabNo) {
        this.cabNo = cabNo;
    }

    public int getNumerOfSeats() {
        return numerOfSeats;
    }

    public void setNumerOfSeats(int numerOfSeats) {
        this.numerOfSeats = numerOfSeats;
    }

    public int getFarPerKm() {
        return farPerKm;
    }

    public void setFarPerKm(int farPerKm) {
        this.farPerKm = farPerKm;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }







    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String company;
    String carModel;

    String carType;


    String cabNo;
    int numerOfSeats;

    int farPerKm;

    boolean available;


}