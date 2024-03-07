package com.example.driverlogapp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.driverlogapp2.Ad.ListAdapter
import com.example.driverlogapp2.Ad.ListData
import com.example.driverlogapp2.DataStructs.ApiResponse
import com.example.driverlogapp2.DataStructs.Route
import com.example.driverlogapp2.DataStructs.RouteNVanger
import com.example.driverlogapp2.DataStructs.Vanger.Vanger
import com.example.driverlogapp2.DataStructs.Vanger.Vangers
import com.example.driverlogapp2.databinding.ActivityMyRequests2Binding
import com.example.driverlogapp2.utils.data.ApiManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*
import kotlin.collections.ArrayList

class MyRequests : AppCompatActivity() {

    lateinit var binding: ActivityMyRequests2Binding
    lateinit var listAdapter: ListAdapter
    val dataArrayList = ArrayList<ListData>()
    lateinit var listData: ListData

    private lateinit var apiManager: ApiManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyRequests2Binding.inflate(getLayoutInflater());
//        setContentView(R.layout.activity_my_requests2)
        setContentView(binding.getRoot());



//        Get VANGERS by driver_id (global)
        apiManager = ApiManager(this)


        apiManager.sendFakePostRequest("http://${AppMuch.localIP}:8008/vangers/${AppMuch.driverID}/by_driver") { response ->
            response?.let {
                try {
                    val gson = Gson()
                    val vangers: List<Vanger> =
                        gson.fromJson(it.toString(), object : TypeToken<List<Vanger>>() {}.type)

                    vangersToRoutes(vangers)
                } catch (e: JsonSyntaxException) {
                    Log.e("ProfileMenu", "Error parsing JSON", e)
                }
            }
        }
//        Get ROUTES with pages from server
//        apiManager = ApiManager(this)
//
//        apiManager.sendGetRequest("http://${AppMuch.localIP}:5000/deals/?page=0&page_size=7&type=route") { response ->
//            response?.let {
//                try {
//                    val gson = Gson()
//                    val apiResponse: ApiResponse = gson.fromJson(it.toString(), ApiResponse::class.java)
//
////                    for (route in apiResponse.routes) {
////                        Log.d("ProfileMenu", "Route ID: ${route.orders[0].id}")
////                    }
//
//                    showViews(apiResponse)
//                    Toast.makeText(this@MyRequests, "Request successful", Toast.LENGTH_SHORT).show()
//                } catch (e: JsonSyntaxException) {
//                    Log.e("ProfileMenu", "Error parsing JSON", e)
//                }
//            }
//        }
//        END OF GETTING DATA

//        Start Test

//        End of test

    }

    var completedRequestsCount = 0
    var totalRequestsCount = 0

    // All ROUTES By VANGERS
    fun vangersToRoutes(vangers: List<Vanger>) {
        var routeNVangers: MutableList<RouteNVanger> = mutableListOf()

        totalRequestsCount = vangers.size
        Log.d("Vangers count", totalRequestsCount.toString())

        for (vanger in vangers) {
            apiManager = ApiManager(this)

            apiManager.sendGetRequest("http://${AppMuch.localIP}:5000/deals/?vanger=${vanger.id}&done=false") { response ->
                response?.let {
                    try {
                        val gson = Gson()
                        val apiResponse: ApiResponse = gson.fromJson(it.toString(), ApiResponse::class.java)

                        for (route in apiResponse.routes) {
                            routeNVangers.add(RouteNVanger(route, vanger))
                        }

                        completedRequestsCount++

                        if (completedRequestsCount == totalRequestsCount) {
                            Log.d("_____________________", "_________");
                            showViews(routeNVangers)
                        }

//                        Toast.makeText(this@MyRequests, "Request successful", Toast.LENGTH_SHORT).show()
                    } catch (e: JsonSyntaxException) {
                        Log.e("ProfileMenu", "Error parsing JSON", e)
                    }
                }
            }
        }

    }

    // ASSIGN VIEWS
    fun showViews(routeNVangers: MutableList<RouteNVanger>) {
        var requestLIST: MutableList<ListData> = mutableListOf()


        for(routeNVanger in routeNVangers) {
            var route : Route = routeNVanger.route
//            Is Complex
            var isComplexText = ""
            if (route.waypoints.points.size > 2) {
                isComplexText = "Сложный маршрут"
            } else {
                isComplexText = "Простой маршрут"
            }

//            DateTime
            var startUnixDateTime = route.time?.beginDate;
            var startTextDate = convertUnixTimeToFormattedDate(startUnixDateTime)

//            Time
            var startTextTime = convertUnixTimeToFormattedTime(startUnixDateTime)



            requestLIST.add(
                ListData(
                    route.waypoints.points[0].city, route.waypoints.points[route.waypoints.points.size-1].city, startTextDate, startTextTime, routeNVanger.vanger.car.numberOfTransport,
                    "Пассажирский", route.id, 0, isComplexText)
            );
            Log.d("--- ADDED", "inloop");
        }

//        requestLIST.add(
//            ListData(
//                "1", "2", "3", "4", "5",
//                "Пассажирский", "1", 0, "yes")
//        );
        Log.d("--- ADDED", "outloop");

        for (request in requestLIST) {
            dataArrayList.add(request);
        }
        val listAdapter = ListAdapter(
            this@MyRequests,
            dataArrayList
        );
        binding.requestsList.setAdapter(listAdapter);
        binding.requestsList.setClickable(true);

        binding.requestsList.setOnItemClickListener { parent, view, i, id ->
            val intent = Intent(this@MyRequests, Request_Details::class.java)
            intent.putExtra("transportType", requestLIST[i].transportType)
            intent.putExtra("isActive", requestLIST[i].isActive)
            intent.putExtra("carNumber", requestLIST[i].carNumber)
            intent.putExtra("dateStr", requestLIST[i].dateStr)
            intent.putExtra("timeStr", requestLIST[i].timeStr)
            intent.putExtra("id", requestLIST[i].id)
            startActivity(intent)
            Log.d("0000000000000000", i.toString());
        }

        Log.d("0010010101010101", requestLIST.size.toString())
    }

    fun convertUnixTimeToFormattedDate(unixTime: Long?): String {
        if (unixTime == null) {
            return "Ошибка даты"
        }


        val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale("ru", "RU"))
        val date = Date(unixTime * 1000)


        return dateFormat.format(date)
    }

    fun convertUnixTimeToFormattedTime(unixTime: Long?): String {
        if (unixTime == null) {
            return "00"
        }

        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        val date = Date(unixTime * 1000)

        return dateFormat.format(date)
    }


}