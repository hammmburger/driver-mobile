package com.example.driverlogapp2.DataStructs

data class ApiResponse(
    val orders: List<Order>,
    val routes: List<Route>
)