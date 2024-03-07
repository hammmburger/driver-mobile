package com.example.driverlogapp2.Ad;

public class ListData {
    public String city_from, city_to, dateStr, timeStr;
    public String carNumber, transportType, isComplexStr;
    public int isActive;
    public String id;

    public ListData(String city_from, String city_to, String dateStr, String timeStr, String carNumber, String transportType, String id, int isActive, String isComplexStr) {
        this.city_from = city_from;
        this.city_to = city_to;
        this.dateStr = dateStr;
        this.timeStr = timeStr;
        this.carNumber = carNumber;
        this.transportType = transportType;
        this.id = id;
        this.isActive = isActive;
        this.isComplexStr = isComplexStr;
    }
}