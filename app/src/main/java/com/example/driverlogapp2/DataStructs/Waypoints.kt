package com.example.driverlogapp2.DataStructs

data class Waypoints(
    val points: List<BackPoint>,
    val times: List<Long>,
    var currentPasTotal: Int,
    var currentCargoTotal: Int,
)