package com.example.notbored.APIServices

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {

    @GET //get request for an specific type of activity
    suspend fun getActivities(@Url activity: String): Response<ActivityResponse>

    @GET("activity/") //get request for an activity of random type
    suspend fun getRandomActivity(): Response<ActivityResponse>
}