package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.ListCategoriaResPonseKT
import com.example.ristorantehttp.container.ResponseCategory
import com.example.ristorantehttp.container.SaveCategory
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceSaveCategory {

    @POST("/api/v1/category/save1")
    fun saveCategory(@Body data: SaveCategory): Call<ResponseCategory>
}