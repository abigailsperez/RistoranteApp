package com.example.ristorante.services

import com.example.ristorante.container.Category
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceCategory {

    @POST("/api/v1/category/save")
    fun save(@Body data: Category): Call<Category>

    @GET("/api/v1/category/list/{id}")
    fun getAll(@Path("id") id: Long): Call<List<Category>>

    @GET("/api/v1/category/{id}")
    fun getById(@Path("id") id: Long): Call<Category>

}