package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.ListBillResPonseKT
import com.example.ristorantehttp.container.ResponseBill
import com.example.ristorantehttp.container.SaveBill
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceBill {

    @POST("/api/v1/bill/save1")
    fun saveBill(@Body data: SaveBill): Call<ResponseBill>

    @GET("/api/v1/bill/list/{id}")
    fun getBill(@Path("id") value: Long): Call<List<ListBillResPonseKT>>
}