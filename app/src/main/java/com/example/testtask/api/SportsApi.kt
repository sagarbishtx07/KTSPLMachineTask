package com.example.testtask.api

import com.example.testtask.model.SportListApiResponse
import retrofit2.http.GET

interface SportsApi {
    @GET("mock/get_all_sport_list")
    suspend fun getAllSports(): SportListApiResponse
}