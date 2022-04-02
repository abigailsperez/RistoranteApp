package com.example.ristorante.services

import com.example.ristorante.container.UserGroup
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceUserGroup {

    @GET("/api/v1/usergroup/{id}")
    fun getById(@Path("id") id: Long): Call<UserGroup>

}