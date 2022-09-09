package com.example.notbored.APIServices

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


private const val BASE_URL = "http://www.boredapi.com/api/"

/*//Creates a retrofit instance*/
fun getRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
/*//Provides an implementation of the api endpoints defined in APIService interface*/
fun provideApiService(): APIService {
    return getRetrofit()
        .create(APIService::class.java)
}