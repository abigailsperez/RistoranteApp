package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.ListMenuResPonseKT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceListMenu {
    @GET("/api/v1/menu/list/{id}")
    fun getReceive(@Path("id") value: Long): Call<List<ListMenuResPonseKT>>
}