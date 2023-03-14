package com.example.pagingbasic.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.pagingbasic.data.api.SimpleApi
import com.example.pagingbasic.data.model.Post
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class MyPagingSource(
    private val simpleApi : SimpleApi,
    private val userId : Int
): PagingSource<Int, Post>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Post> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = simpleApi.getCustomPost2(
                userId = userId,
                sort = "id",
                order = "asc"
            )
            val post = response.body()

            LoadResult.Page(
                data = post!!,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = position + 1
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Post>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}