package com.example.ristorante.services

import com.example.ristorante.container.Bill
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceBill {

    @POST("/api/v1/bill/save")
    fun save(@Body data: Bill): Call<Bill>

    @GET("/api/v1/bill/list/{id}")
    fun getAll(@Path("id") restaurant: Long): Call<List<Bill>>

}