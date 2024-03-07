package com.example.driverlogapp2.Ad.AdDet;

import java.util.ArrayList;
import java.util.List;

public class Point {
    public String address;
    public String dateTimeStr;
    public ArrayList<String> newPassenegers;
    public ArrayList<String> exitPassengers;
    public Integer totalPassengers;
    public ArrayList<String> newCargo;
    public ArrayList<String> exitCargo;
    public Integer totalCargo;

    public Point(String address, String dateTimeStr, ArrayList<String> newPassenegers,
                 ArrayList<String> exitPassengers, Integer totalPassengers, ArrayList<String> newCargo,
                 ArrayList<String> exitCargo, Integer totalCargo) {
        this.address = address;
        this.dateTimeStr = dateTimeStr;
        this.newPassenegers = newPassenegers;
        this.exitPassengers = exitPassengers;
        this.totalPassengers = totalPassengers;
        this.newCargo = newCargo;
        this.exitCargo = exitCargo;
        this.totalCargo = totalCargo;
    }
}
