package com.example.pagingbasic.paging

import androidx.lifecycle.ViewModel
import androidx.paging.PagingData
import com.example.pagingbasic.data.PostRepository
import com.example.pagingbasic.data.model.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val repository: PostRepository
) : ViewModel() {

    fun getPosts(): Flow<PagingData<Post>> = repository.getPosts()
}