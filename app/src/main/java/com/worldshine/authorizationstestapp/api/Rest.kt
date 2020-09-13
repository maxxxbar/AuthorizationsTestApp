package com.worldshine.authorizationstestapp.api

import com.worldshine.authorizationstestapp.pojo.Response
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Rest {

    @GET("forecasts/v1/daily/1day/{cityID}")
    fun getForecast(
        @Path("cityID") cityID: Int = 293970,
        @Query("apikey") apikey: String = "AOxnC24tk7aG5Qoga0pjqqFw4VmxJhmu",
        @Query("language") language: String = "ru-RU",
        @Query("metric") metric: String = "true"
    ): Single<Response>
}