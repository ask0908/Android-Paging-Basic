package com.example.pagingbasic.data.api

import com.example.pagingbasic.data.model.Post
import retrofit2.http.GET
import retrofit2.http.Query

interface JsonPlaceHolderApi {
    @GET("posts")
    suspend fun getPosts(
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int
    ): List<Post>
}