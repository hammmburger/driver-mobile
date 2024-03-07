package com.example.driverlogapp2.utils.data
import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class ApiManager(private val context: Context) {

    private val apiRequestQueue: RequestQueue = Volley.newRequestQueue(context)

//    For JSON Structure
    fun sendGetRequest(url: String, callback: (JSONObject?) -> Unit) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener<JSONObject> { response ->
                // Handle successful response
                Log.d("ApiManager", "Response: $response")
                callback(response)
            },
            Response.ErrorListener { error ->
                // Handle errors
                val errorMessage = "Error: ${error.message}"
                Log.e("ApiManager", errorMessage)
                callback(null)
            })

        // Add the request to the queue
        apiRequestQueue.add(jsonObjectRequest)
    }

//    For Array Structure
    fun sendFakePostRequest(url: String, completionHandler: (response: JSONArray?) -> Unit) {
        val request = JsonArrayRequest(
            Request.Method.POST, url, null,
            Response.Listener { response ->
                completionHandler(response)
            },
            Response.ErrorListener { error ->
                Log.e("ApiManager", "Error: $error")
                completionHandler(null)
            })

        // Adding the request to the queue
        apiRequestQueue.add(request)
    }





}
