package com.example.driverlogapp2.DataStructs

data class Cargo(
    val unit: String,
    val numberOfPassengersInCar: Int,
    val amountOfCargoInCar: Int,
    val passengers: List<Passenger>,
    val description: String,
    val freights: List<Freight>
)