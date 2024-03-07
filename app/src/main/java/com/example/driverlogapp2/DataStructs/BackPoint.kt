package com.example.driverlogapp2.DataStructs

data class BackPoint(
    val latitude: String,
    val longitude: String,
    val address: String,
    val city: String,
    val pointType: String,
    var pasNew: ArrayList<String>,
    var pasDel: ArrayList<String>,
//    var pasTotal: Int,
    var cargoNew: ArrayList<String>,
    var cargoDel: ArrayList<String>,
//    var cargoTotal: Int,
    val _id: String,
)
