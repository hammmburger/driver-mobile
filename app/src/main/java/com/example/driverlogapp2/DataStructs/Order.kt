package com.example.driverlogapp2.DataStructs

data class Order(
    val id: String,
    val clientId: String,
    val routeId: String,
    val cargo: Cargo,
    val waypoints: Waypoints
)