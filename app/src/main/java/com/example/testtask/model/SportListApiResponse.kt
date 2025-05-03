package com.example.testtask.model

data class SportListApiResponse(
    val data: List<SportsTable>,
    val message: String,
    val status: String,
    val statusCode: Int
)