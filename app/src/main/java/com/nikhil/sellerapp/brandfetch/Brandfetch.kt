package com.nikhil.sellerapp.brandfetch

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface Brandfetch {
    @GET("v2/brands/{domain}")
    fun getbrand(
        @Path("domain") domain:String,
        @Header("Authorization") apikey:String
    ): Call<BrandResponse>

    @GET("v2/search/{query}")
    fun getname(
        @Path("query") query:String,
        @Header("Authorization") apikey:String
    ): Call<List<Brandname>>
}