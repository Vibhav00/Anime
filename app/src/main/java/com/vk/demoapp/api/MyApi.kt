package com.vk.demoapp.api

import com.vk.demoapp.data.Animes
import com.vk.demoapp.data.DescAnime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MyApi {


    @GET("v4/top/anime/")
    suspend fun getAnimes(): Response<Animes>


    @GET("v4/anime/{id}")
    suspend fun getSpecific(@Path("id") id: Int): Response<DescAnime>

}