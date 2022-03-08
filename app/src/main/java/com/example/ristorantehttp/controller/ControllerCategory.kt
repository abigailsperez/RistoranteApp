package com.example.ristorantehttp.controller

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.example.ristorantehttp.model.entity.Category
import com.example.ristorantehttp.model.entity.User
import com.example.ristorantehttp.model.repository.APIService
import com.example.ristorantehttp.view.AddCategoryActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonNull
import com.google.gson.JsonParser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class ControllerCategory {

    val baseURL = "http://192.168.1.68:8080"


    fun saveCategory(name: String) {

        // Create Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(baseURL)
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)

        // Create JSON using JSONObject
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("restaurant", 8)

        // Convert JSONObject to String
        val jsonObjectString = jsonObject.toString()

        // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
        val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

        CoroutineScope(Dispatchers.IO).launch {
            // Do the POST request and get response
            val response = service.saveCategory(requestBody)

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

                    var category = Gson().fromJson(prettyJson, Category::class.java) // El json convertido a instancia de category

                } else {

                    Log.e("RETROFIT_ERROR", response.code().toString())

                }
            }
        }
    }

    fun getCategorys() {

         // Create retrofit
         val retrofit = Retrofit.Builder()
             .baseUrl(baseURL)
             .build()

         // Create Service
         val service = retrofit.create(APIService::class.java)


         CoroutineScope(Dispatchers.IO).launch {
             /*
              * For @Query: You need to replace the following line with val response = service.getEmployees(2)
              * For @Path: You need to replace the following line with val response = service.getEmployee(53)
              */

             // Do the GET request and get response
             val response = service.getCategorys()
             //val response1 = service.getCategory("2")

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

                 } else {

                     Log.e("RETROFIT_ERROR", response.code().toString())

                 }
             }
         }
        return
    }

    fun updateCategory(name: String) {

         // Create Retrofit
         val retrofit = Retrofit.Builder()
             .baseUrl(baseURL)
             .build()

         // Create Service
         val service = retrofit.create(APIService::class.java)

         // Create JSON using JSONObject
         val jsonObject = JSONObject()
        jsonObject.put("id", 1)
         jsonObject.put("name", name)
         jsonObject.put("restaurant", 2)

         // Convert JSONObject to String
         val jsonObjectString = jsonObject.toString()

         // Create RequestBody ( We're not using any converter, like GsonConverter, MoshiConverter e.t.c, that's why we use RequestBody )
         val requestBody = jsonObjectString.toRequestBody("application/json".toMediaTypeOrNull())

         CoroutineScope(Dispatchers.IO).launch {

             // Do the PUT request and get response
             val response = service.updateCategory(requestBody)

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

                 } else {

                     Log.e("RETROFIT_ERROR", response.code().toString())

                 }
             }
         }
    }
}