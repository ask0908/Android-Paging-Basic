package com.example.pagingbasic.paging

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.pagingbasic.data.PagingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: PagingRepository
): ViewModel() {
    private val myCustomPosts2: MutableLiveData<Int> = MutableLiveData()

    val result = myCustomPosts2.switchMap { queryString ->
        repository.getPost(queryString).cachedIn(viewModelScope)
    }

    fun searchPost(userId: Int) {
        myCustomPosts2.value = userId
    }
}