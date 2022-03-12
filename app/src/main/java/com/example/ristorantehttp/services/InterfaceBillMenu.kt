package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.ListBillMenuResPonseKT
import com.example.ristorantehttp.container.ListBillResPonseKT
import com.example.ristorantehttp.container.ResponseBillMenu
import com.example.ristorantehttp.container.SaveBillMenu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceBillMenu {

    @POST("/api/v1/bill_menu/save1")
    fun saveBillMenu(@Body data: SaveBillMenu): Call<ResponseBillMenu>

    @GET("/api/v1/bill_menu/list/{id}")
    fun getBillMenu(@Path("id") value: Long): Call<List<ListBillMenuResPonseKT>>

    //@POST("/api/v1/bill/save1")
    //fun deleteBillMenu(@Body data: SaveBillMenu): Call<ResponseBillMenu>
}