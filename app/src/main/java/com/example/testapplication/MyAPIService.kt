package com.example.testapplication

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MyAPIService {

    //get random image with hard coded token
    @GET("randomimage?category=")
    suspend fun fetchRandImage(
        @Header("X-Api-Key") token: String,
        @Header("Accept") accept: String,
        @Query("category") category: String)
            : Response<ResponseBody>

    //get calorie info for given activity with hard coded token
    @GET("caloriesburned?activity=")
    suspend fun fetchCalorieInfo(
        @Header("X-Api-Key") token: String,
        @Header("Accept") accept: String,
        @Query("activity") activity: String)
            : Response<List<calInfo>>
}