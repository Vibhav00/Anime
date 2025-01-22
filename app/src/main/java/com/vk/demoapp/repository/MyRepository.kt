package com.vk.demoapp.repository

import com.vk.demoapp.api.ApiClient
import com.vk.demoapp.data.Animes
import com.vk.demoapp.data.DescAnime
import retrofit2.Response

// my repository for api calls
class MyRepository() : ParentRepository {
    override suspend fun getAllAnime(): Response<Animes> {
        return ApiClient.getApi().getAnimes()
    }

    override suspend fun getSA(id: Int): Response<DescAnime> {
        return ApiClient.getApi().getSpecific(id)
    }
}