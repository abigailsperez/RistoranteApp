package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.ListCategoriaResPonseKT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceListCategory {
    @GET("/api/v1/category/list/{id}")
    fun getReceive(@Path("id") value: Long): Call<List<ListCategoriaResPonseKT>>

}