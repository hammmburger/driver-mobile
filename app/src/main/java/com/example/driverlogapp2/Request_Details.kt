package com.example.driverlogapp2

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.example.driverlogapp2.Ad.AdDet.ListPointsAdapter
import com.example.driverlogapp2.Ad.AdDet.Point
import com.example.driverlogapp2.Ad.ListAdapter
import com.example.driverlogapp2.Ad.ListData
import com.example.driverlogapp2.DataStructs.ApiResponse
import com.example.driverlogapp2.DataStructs.Route
import com.example.driverlogapp2.DataStructs.Vanger.Vanger
import com.example.driverlogapp2.databinding.ActivityMyRequests2Binding
import com.example.driverlogapp2.databinding.ActivityRequestDetailsBinding
import com.example.driverlogapp2.utils.data.ApiManager
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Request_Details : AppCompatActivity() {

    lateinit var binding: ActivityRequestDetailsBinding
    lateinit var pointsAdapter: ListPointsAdapter
    val pointsArrayList = ArrayList<Point>()
    lateinit var point: Point
    lateinit var requestID: String

    lateinit var activeState: Integer

    private lateinit var apiManager: ApiManager


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRequestDetailsBinding.inflate(getLayoutInflater())
//        setContentView(R.layout.activity_request_details)
        setContentView(binding.getRoot())

        activeState = Integer(0)

        apiManager = ApiManager(this)

        requestID = "0"
        val intent = this.getIntent()
        if (intent != null) {
            binding.transportType.setText(intent.getStringExtra("transportType"))
            binding.transportNumber.setText(intent.getStringExtra("carNumber"))
            binding.startTime.setText(intent.getStringExtra("timeStr"))
            binding.startDate.setText(intent.getStringExtra("dateStr"));
            binding.title.setText("Поездка по маршруту " + intent.getStringExtra(("dateStr")));
            requestID = intent.getStringExtra("id")!!
        }

//        Get Route By id_route
        apiManager = ApiManager(this)

        apiManager.sendGetRequest("http://${AppMuch.localIP}:5000/routes/std/${requestID}") { response ->
            response?.let {
                try {
                    val gson = Gson()
                    val apiResponse: Route = gson.fromJson(it.toString(), Route::class.java)

//                    for (route in apiResponse.routes) {
//                        Log.d("ProfileMenu", "Route ID: ${route.orders[0].id}")
//                    }

                    showViews(apiResponse)
                } catch (e: JsonSyntaxException) {
                    Log.e("ProfileMenu", "Error parsing JSON", e)
                }
            }
        }

        //        Start Request
        apiManager = ApiManager(this)

        binding.startRequestButton.setOnClickListener {

            if (activeState == Integer(0)) {
                // Inactive to Active
                apiManager.sendFakePostRequest("http://${AppMuch.localIP}:5000/routes/std/execution?id=${requestID}&state=start") { response ->
                    response?.let {
                        try {

                        } catch (e: JsonSyntaxException) {
                            Log.e("ProfileMenu", "Error parsing JSON", e)
                        }
                    }
                }

                binding.startRequestButton.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
                binding.startRequestButton.setText("Завершить поездку")
                activeState = Integer(1)
            } else if (activeState == Integer(1)) {
                apiManager.sendFakePostRequest("http://${AppMuch.localIP}:5000/routes/std/execution?id=${requestID}&state=stop") { response ->
                    response?.let {
                        try {

                        } catch (e: JsonSyntaxException) {
                            Log.e("ProfileMenu", "Error parsing JSON", e)
                        }
                    }
                }

                activeState = Integer(-1)
                binding.startRequestButton.backgroundTintList = getColorStateList(android.R.color.darker_gray)
                binding.startRequestButton.setText("Поездка завершена")
                // Active to Done
            } else if (activeState == Integer(-1)) {
                // Done...
            }



            return@setOnClickListener
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun showViews(route: Route) {
        // TODO: Убрать костыль
        if (route.active) {
            activeState = Integer(1)
            binding.startRequestButton.backgroundTintList = getColorStateList(android.R.color.holo_red_light)
            binding.startRequestButton.setText("Завершить поездку")
        }

        var pointLIST: MutableList<Point> = mutableListOf()

        var pointCounter : Int = -1
        route.waypoints.currentPasTotal = 0
        route.waypoints.currentCargoTotal = 0
        for (point in route.waypoints.points) {
            pointCounter++;

            point.pasNew = arrayListOf()
            point.pasDel = arrayListOf()

            point.cargoNew = arrayListOf()
            point.cargoDel = arrayListOf()
            // TODO: Также сделать перебор orders
            for (passenger in route.orders[0].cargo.passengers) {
                if (passenger.startIndex == pointCounter) {
                    point.pasNew.add(passenger.name)
                }
            }
            for (passenger in route.orders[0].cargo.passengers) {
                if (passenger.endIndex == pointCounter) {
                    point.pasDel.add(passenger.name)
                }
            }

            route.waypoints.currentPasTotal += point.pasNew.size;
            route.waypoints.currentPasTotal -= point.pasDel.size;


            for (cargo in route.orders[0].cargo.freights) {
                if (cargo.startIndex == pointCounter) {
                    point.cargoNew.add(cargo.description)
                }
            }
            for (cargo in route.orders[0].cargo.freights) {
                if (cargo.endIndex == pointCounter) {
                    point.cargoDel.add(cargo.description)
                }
            }

            route.waypoints.currentCargoTotal += point.cargoNew.size;
            route.waypoints.currentCargoTotal -= point.cargoDel.size;

            var unixTime = route.waypoints.times[pointCounter]
            var estimatedTime =  convertUnixTimeToFormattedTime(unixTime) + "   " + convertUnixTimeToFormattedDate(unixTime)

            pointLIST.add(
                Point(point.address, estimatedTime, point.pasNew, point.pasDel, route.waypoints.currentPasTotal,
                    point.cargoNew, point.cargoDel, route.waypoints.currentCargoTotal)
            );
        }


//        pointLIST.add(
//            Point("Новосибирск, ул. Ленина 22", "20:00   Январь 16 2024", ArrayList<String>(listOf()), ArrayList<String>(listOf("Ельцин А.")), ArrayList<String>(listOf("Ильин Б.")),
//                ArrayList<String>(listOf("Стаканы 15кг")), ArrayList<String>(listOf("Папки 5кг")), ArrayList<String>(listOf("Папки 3кг", "Стаканы 2кг", "Стаканы 15кг")))
//        );
//        pointLIST.add(
//            Point("Бийск, ул. Ленина 34", "12:00   Январь 17 2024", ArrayList<String>(listOf()), ArrayList<String>(listOf("Ильин Б.")), ArrayList<String>(listOf()),
//                ArrayList<String>(listOf()), ArrayList<String>(listOf("Папки 3кг", "Стаканы 2кг", "Стаканы 15кг")), ArrayList<String>(listOf()))
//        );

        for (point in pointLIST) {
            pointsArrayList.add(point);
        }
        val listAdapter = ListPointsAdapter(
            this@Request_Details,
            pointsArrayList
        );

        binding.routesList.setAdapter(listAdapter);
        binding.routesList.setClickable(true);

        binding.routesList.setOnItemClickListener { parent, view, i, id ->
            val intent = Intent(this@Request_Details, PassCargoDetails::class.java)
            intent.putExtra("address", pointLIST[i].address)
            intent.putExtra("newPassengers", pointLIST[i].newPassenegers);
            intent.putExtra("exitPassengers", pointLIST[i].exitPassengers);
            intent.putExtra("totalPassengers", pointLIST[i].totalPassengers);
            intent.putExtra("newCargo", pointLIST[i].newCargo);
            intent.putExtra("exitCargo", pointLIST[i].exitCargo);
            intent.putExtra("totalCargo", pointLIST[i].totalCargo);
            startActivity(intent)
        }
    }
}

// TODO: Move to utils
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
