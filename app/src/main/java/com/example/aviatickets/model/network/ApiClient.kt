package com.example.aviatickets.model.network

import com.example.aviatickets.model.entity.Offer
import retrofit2.http.GET
import retrofit2.Call

interface ApiClient {


    @GET("https://my-json-server.typicode.com/estharossa/fake-api-demo/offer_list/")
    fun getData(): Call<List<Offer>>
    /**
     * think about performing network request
     */
}