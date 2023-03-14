package com.example.pagingbasic.data.api

import com.example.pagingbasic.data.model.Post
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SimpleApi {
    @GET("posts")
    suspend fun getCustomPost2(
        @Query("userId") userId : Int,
        @Query("_sort") sort : String,
        @Query("_order") order : String
    ): Response<List<Post>>
}