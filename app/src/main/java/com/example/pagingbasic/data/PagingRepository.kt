package com.example.pagingbasic.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.pagingbasic.data.api.SimpleApi
import com.example.pagingbasic.paging.MyPagingSource
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PagingRepository @Inject constructor(
    private val simpleApi: SimpleApi
) {
    fun getPost(userId: Int) =
        Pager(
            config = PagingConfig(
                pageSize = 5,
                maxSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MyPagingSource(simpleApi, userId) }
        ).liveData
}