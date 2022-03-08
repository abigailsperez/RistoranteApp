package com.example.ristorantehttp.services

import com.example.ristorantehttp.container.ListSessionResPonseKT
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface InterfaceListSession {
    @GET("/api/v1/session/all/{id}/1")
    fun getReceiveIn(@Path("id") value: Long): Call<List<ListSessionResPonseKT>>

    @GET("/api/v1/session/all/{id}/0")
    fun getReceiveOut(@Path("id") value: Long): Call<List<ListSessionResPonseKT>>

}