package com.example.pagingbasic.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingbasic.data.api.JsonPlaceHolderApi
import com.example.pagingbasic.data.model.Post

class PostPagingSource(
    private val api: JsonPlaceHolderApi
): PagingSource<Int, Post>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        try {
            val page = params.key ?: 1
            val pageSize = params.loadSize

            val response = api.getPosts(page, pageSize)

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (response.isEmpty()) null else page + 1

            return LoadResult.Page(
                data = response,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}