package com.example.driverlogapp2.DataStructs

data class Route(
    val id: String,
    val orders: List<Order>,
    val waypoints: Waypoints,
    val times: List<Long>,
    val distance: Float,
    val clients: List<String>,
    val done: Boolean,
    val active: Boolean,
    val vanger: String,
    val time: Time
)