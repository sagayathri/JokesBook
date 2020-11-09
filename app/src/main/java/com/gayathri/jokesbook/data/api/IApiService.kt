package com.gayathri.jokesbook.data.api

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface IApiService {
    @GET("/jokes/{type}/ten")
    suspend fun getJokesByType(
        @Path("type") type: String
    ): Response<ResponseBody>
}