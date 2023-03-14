package com.example.pagingbasic.view

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.example.pagingbasic.R
import com.example.pagingbasic.data.PagingRepository
import com.example.pagingbasic.data.api.SimpleApi
import com.example.pagingbasic.databinding.ActivityMainBinding
import com.example.pagingbasic.paging.MainViewModel
import com.example.pagingbasic.paging.MyLoadStateAdapter
import com.example.pagingbasic.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    private val viewModel: MainViewModel by viewModels()
    private val myAdapter: MyAdapter by lazy {
        MyAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            recyclerView.apply {
                adapter = myAdapter.withLoadStateHeaderAndFooter(
                    header = MyLoadStateAdapter { myAdapter.retry() },
                    footer = MyLoadStateAdapter { myAdapter.retry() }
                )
                setHasFixedSize(true)
            }

            button.setOnClickListener {
                if (editTextView.text.toString().isNotEmpty()) {
                    searchPost()

                    // API 호출 후 editText 포커싱 삭제
                    val inputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(editTextView.windowToken, 0)
                }
            }

            myAdapter.addLoadStateListener { combinedLoadStates ->
                progressBar.isVisible = combinedLoadStates.source.refresh is LoadState.Loading
                recyclerView.isVisible = combinedLoadStates.source.refresh is LoadState.NotLoading

                // 로딩 중 에러 발생 시
                retryButton.isVisible = combinedLoadStates.source.refresh is LoadState.Error
                errorText.isVisible = combinedLoadStates.source.refresh is LoadState.Error

                // 활성된 로드 작업과 에러가 없음 && 로드 불가능 && 가져올 데이터가 없음
                if (combinedLoadStates.source.refresh is LoadState.NotLoading && combinedLoadStates.append.endOfPaginationReached && myAdapter.itemCount < 1) {
                    recyclerView.isVisible = false
                    emptyText.isVisible = true
                } else {
                    emptyText.isVisible = false
                }
            }

            retryButton.setOnClickListener { myAdapter.retry() }
        }
    }

    private fun ActivityMainBinding.searchPost() {
        viewModel.searchPost(editTextView.text.toString().toInt())
        viewModel.result.observe(this@MainActivity) {
            // ListAdapter와 다르게 1번 인자로 getLifecycle을 넘겨야 한다
            myAdapter.submitData(this@MainActivity.lifecycle, it)
        }
    }
}