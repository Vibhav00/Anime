package com.vk.demoapp.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient() {
    companion object {

        const val BASE_URL = "https://api.jikan.moe/"
        private var retrofit: Retrofit? = null


        private fun initializeRetrofit() {
            // For the first Retrofit instance
            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()


        }

        fun getApi(): MyApi {
            if (retrofit == null) {
                initializeRetrofit()
                return retrofit!!.create(MyApi::class.java)
            }
            return retrofit!!.create(MyApi::class.java)
//            checkNotNull(retrofit) { "Retrofit is not initialized. Call initializeRetrofit() first." }
        }

    }
}
