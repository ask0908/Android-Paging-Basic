package com.example.pagingbasic.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.pagingbasic.data.api.JsonPlaceHolderApi
import com.example.pagingbasic.data.model.Post
import com.example.pagingbasic.paging.PostPagingSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

class PostRepository(
    private val api: JsonPlaceHolderApi,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    fun getPosts(): Flow<PagingData<Post>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { PostPagingSource(api) }
        ).flow.flowOn(ioDispatcher)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}