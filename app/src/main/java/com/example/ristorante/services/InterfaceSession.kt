package com.example.ristorante.services

import com.example.ristorante.containers.SessionContainer
import com.example.ristorante.entity.Session
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface InterfaceSession {

    @GET("/api/v1/session/all/{id}/1")
    fun getAllIn(@Path("id") value: Long): Call<List<SessionContainer>>

    @GET("/api/v1/session/all/{id}/0")
    fun getAllOut(@Path("id") value: Long): Call<List<SessionContainer>>

    @POST("/api/v1/session/check-in/{id}")
    fun checkIn(@Path("id") value: Long): Call<Session>

    @POST("/api/v1/session/check-out/{id}")
    fun checkOut(@Path("id") value: Long): Call<Session>

}