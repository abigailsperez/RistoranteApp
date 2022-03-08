package com.example.ristorantehttp.controller

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.ristorantehttp.model.entity.User
import com.example.ristorantehttp.model.repository.RestApiUserV1
import com.example.ristorantehttp.view.MenuAdminActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.Retrofit

class ControllerUser {

    val baseURL = "http://192.168.0.9:8080"

    fun login(email: String, password: String) : String{
        var returnValue:String=""
        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .build()

        // Create Service
        val service = retrofit.create(RestApiUserV1::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("email", email)
        jsonObject.put("password", password)


        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.login(requestBody)


            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // Convert raw JSON to pretty JSON using GSON library
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val prettyJson = gson.toJson(
                        JsonParser.parseString(
                            response.body()
                                ?.string() // About this thread blocking annotation : https://github.com/square/retrofit/issues/3255
                        )
                    )

                    Log.d("Pretty Printed JSON :", prettyJson)

                    var user = Gson().fromJson(
                        prettyJson,
                        User::class.java
                    ) // El json convertido a instancia de user

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())
                    returnValue = "ccdcdcd"

                }
            }

        }
        return returnValue
    }

}