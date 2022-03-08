package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.LoginUser
import com.example.ristorantehttp.container.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceUser {
    @POST("/api/v1/user/login")
    fun login(@Body data: LoginUser): Call<ResponseLogin>
}