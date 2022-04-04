package com.example.ristorante.services

import com.example.ristorante.entity.Menu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceMenu {

    @POST("/api/v1/menu/save")
    fun save(@Body data: Menu): Call<Menu>

    @GET("/api/v1/menu/list/{id}")
    fun getAll(@Path("id") value: Long): Call<List<Menu>>

    @GET("/api/v1/menu/{id}")
    fun getById(@Path("id") value: Long): Call<Menu>
}