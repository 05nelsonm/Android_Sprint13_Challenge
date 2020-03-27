package com.lambdaschool.sprintchallenge13.retrofit

import com.lambdaschool.sprintchallenge13.model.Makeup
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface MakeupApi {

    @GET("products.json")
    fun getMakeup(@Query("brand") brand: String): Single<Array<Makeup>>
}