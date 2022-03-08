package com.example.ristorantehttp.model.repository

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RestApiUserV1 {

    @POST("/api/v1/user/login")
    suspend fun login(@Body requestBody: RequestBody): Response<ResponseBody>

}