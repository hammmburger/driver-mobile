package com.example.driverlogapp2

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.driverlogapp2.utils.data.ApiManager

class ProfileMenu : AppCompatActivity() {

    private lateinit var myRequestsBtn: RelativeLayout
    private lateinit var openCallBtn: RelativeLayout
    private lateinit var exitBtn: RelativeLayout
    private lateinit var apiManager: ApiManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_menu)

        myRequestsBtn = findViewById(R.id.openRequests)
        openCallBtn = findViewById(R.id.openCall)
        exitBtn = findViewById(R.id.exit)

        openCallBtn.setOnClickListener {
            val phone = "+79130846010"
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)

            return@setOnClickListener
        }


        myRequestsBtn.setOnClickListener {

            val intent = Intent(applicationContext, MyRequests::class.java)
            startActivity(intent)
            finish()

            return@setOnClickListener
        }

        exitBtn.setOnClickListener {

            val intent = Intent(applicationContext, Login::class.java)
            startActivity(intent)
            finish()

            return@setOnClickListener
        }



        apiManager = ApiManager(this)
//        myRequestsBtn.setOnClickListener {
//            myRequestsBtn.setOnClickListener {
//                apiManager.sendGetRequest("http://${AppMuch.localIP}:5000/deals/?page=1&page_size=2&type=route") { response ->
//
//                    response?.let {
//                        try {
//                            val gson = Gson()
//                            val apiResponse: ApiResponse = gson.fromJson(it.toString(), ApiResponse::class.java)
//
//                            for (order in apiResponse.orders) {
//                                Log.d("ProfileMenu", "Order ID: ${order.id}")
//                            }
//
//                            for (route in apiResponse.routes) {
//                                Log.d("ProfileMenu", "Route ID: ${route.orders[0].cargo.description}")
//                            }
//
//                            Toast.makeText(this@ProfileMenu, "Request successful", Toast.LENGTH_SHORT).show()
//                        } catch (e: JsonSyntaxException) {
//                            Log.e("ProfileMenu", "Error parsing JSON", e)
//                        }
//                    }
//
//                }
//            }
//
//            return@setOnClickListener
//        }
    }
}