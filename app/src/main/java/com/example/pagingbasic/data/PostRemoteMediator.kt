@file:OptIn(ExperimentalPagingApi::class)

package com.example.pagingbasic.data

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.example.pagingbasic.data.api.JsonPlaceHolderApi
import com.example.pagingbasic.data.model.Post

class PostRemoteMediator(
    private val api: JsonPlaceHolderApi,
) : RemoteMediator<Int, Post>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, Post>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> {
                return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = false)
                lastItem.id / PAGE_SIZE + 1
            }
        }

        return try {
            val response = api.getPosts(page, PAGE_SIZE)
            val endOfPaginationReached = response.isEmpty()

            MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}