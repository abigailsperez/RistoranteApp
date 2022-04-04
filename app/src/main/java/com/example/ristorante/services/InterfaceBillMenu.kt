package com.example.ristorante.services

import com.example.ristorante.containers.BillMenuContainer
import com.example.ristorante.entity.BillMenu
import retrofit2.Call
import retrofit2.http.*

interface InterfaceBillMenu {

    @POST("/api/v1/bill_menu/save")
    fun save(@Body data: BillMenu): Call<BillMenu>

    @GET("/api/v1/bill_menu/list/{id}")
    fun getAll(@Path("id") bill: Long): Call<List<BillMenuContainer>>

    @GET("/api/v1/bill_menu/{id}")
    fun getById(@Path("id") bill_menu: Long): Call<BillMenu>

    @DELETE("/api/v1/bill_menu/delete/{id}")
    fun deleteById(@Path("id") bill_menu: Long): Call<Void>

}