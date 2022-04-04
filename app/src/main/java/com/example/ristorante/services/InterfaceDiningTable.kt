package com.example.ristorante.services

import com.example.ristorante.entity.DiningTable
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceDiningTable {

    @GET("/api/v1/dt/list/{id}")
    fun getAll(@Path("id") value: Long): Call<List<DiningTable>>

    @POST("/api/v1/dt/save")
    fun save(@Body data: DiningTable): Call<DiningTable>

}