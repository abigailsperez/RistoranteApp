package com.example.ristorantehttp.model.repository

import com.example.ristorantehttp.model.entity.Category
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface APIService {

    @POST("/api/v1/category/save")
    suspend fun saveCategory(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/v1/category/list/8")
    suspend fun getCategorys(): Response<ResponseBody>

    @PUT("/api/v1/category/save")
    suspend fun updateCategory(@Body requestBody: RequestBody): Response<ResponseBody>

   /*@DELETE("/api/v1/category/3")
    suspend fun deleteCategory(): Response<ResponseBody>*/

    @POST("/api/v1/menu/save")
    suspend fun saveMenu(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/v1/menu/list/10")
    suspend fun getMenu(): Response<ResponseBody>

    @PUT("/api/v1/menu/5")
    suspend fun updateMenu(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/v1/session/all/10/{inOut}")
    suspend fun getSession(): Response<ResponseBody>

    @POST("/api/v1/bill/save")
    suspend fun saveBill(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/v1/bill/list/10")
    suspend fun getBills(): Response<ResponseBody>

    @PUT("/api/v1/bill/23")
    suspend fun updateBill(@Body requestBody: RequestBody): Response<ResponseBody>

    @POST("/api/v1/category/save")
    suspend fun saveBill_Menu(@Body requestBody: RequestBody): Response<ResponseBody>

    @GET("/api/v1/category/list/10")
    suspend fun getBill_Menu(): Response<ResponseBody>

    @PUT("/api/v1/category/23")
    suspend fun updateBill_Menu(@Body requestBody: RequestBody): Response<ResponseBody>


}