package com.nikhil.sellerapp.brandfetch

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetroBrand {
        private const val base_url="https://api.brandfetch.io/"
    val instance:Brandfetch by lazy {
        Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Brandfetch::class.java)
    }
}