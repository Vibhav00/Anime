package com.vk.demoapp.repository

import com.vk.demoapp.data.Animes
import com.vk.demoapp.data.DescAnime
import retrofit2.Response

// parent repo for api + test
interface  ParentRepository {
    suspend fun getAllAnime(): Response<Animes>
    suspend fun getSA(id:Int): Response<DescAnime>
}