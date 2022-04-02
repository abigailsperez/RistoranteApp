package com.example.ristorante.services

import com.example.ristorante.container.BillMenu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceBillMenu {

    @POST("/api/v1/bill_menu/save")
    fun save(@Body data: BillMenu): Call<BillMenu>

    @GET("/api/v1/bill_menu/list/{id}")
    fun getAll(@Path("id") bill: Long): Call<List<BillMenu>>

}