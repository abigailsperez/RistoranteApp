package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.ResponseMenu
import com.example.ristorantehttp.container.SaveMenu
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceSaveMenu {

    @POST("/api/v1/menu/save1")
    fun saveMenu(@Body data: SaveMenu): Call<ResponseMenu>
}