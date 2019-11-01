package com.lambdaschool.sprintchallenge13.retrofit

import com.google.gson.Gson
import com.lambdaschool.sprintchallenge13.model.Makeup
import io.reactivex.Single
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface MakeupApi {

    @GET("products.json")
    fun getMakeup(@Query("brand") brand: String): Single<Makeup>

    class Factory {
        companion object {
            val BASE_URL = "http://makeup-api.herokuapp.com/api/v1/"
            val gson = Gson()
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
                level = HttpLoggingInterceptor.Level.BODY
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger)
                .retryOnConnectionFailure(false)
                .readTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(15, TimeUnit.SECONDS)
                .build()

            fun create(): MakeupApi {
                return Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(MakeupApi::class.java)
            }
        }
    }
}