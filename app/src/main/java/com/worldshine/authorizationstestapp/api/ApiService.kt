package com.worldshine.authorizationstestapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiService {
    private const val BASE_URL = "https://dataservice.accuweather.com/"
//    private val httpLoggingInterceptor =
//        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//    private val client = OkHttpClient().newBuilder().addInterceptor(httpLoggingInterceptor).build()
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(BASE_URL)
//        .client(client)
        .build()

    val create: Rest = retrofit.create(Rest::class.java)

}