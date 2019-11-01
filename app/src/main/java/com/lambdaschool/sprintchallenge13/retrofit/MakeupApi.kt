package com.lambdaschool.sprintchallenge13.retrofit

import com.google.gson.Gson
import com.lambdaschool.sprintchallenge13.model.Makeup
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface MakeupApi {

    @GET("products.json")
    fun getMakeup(@Query("brand") brand: String): Single<Makeup>

    class Factory {
        companion object {
            private val BASE_URL = "http://makeup-api.herokuapp.com/api/v1/"
            private val gson = Gson()
        }

        fun create(): MakeupApi {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MakeupApi::class.java)
        }
    }
}