package com.oechslerbernardo.fakestore.hilt.service

import retrofit2.Response
import retrofit2.http.GET

interface ProductsService {

    @GET("products")
    suspend fun getAllProducts(): Response<List<Any>>
}