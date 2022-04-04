package com.example.ristorante.services

import com.example.ristorante.entity.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface InterfaceUser {

    @POST("/api/v1/user/login")
    fun login(@Body data: User): Call<User>

}