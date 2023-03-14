package com.example.pagingbasic.view

import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.pagingbasic.R
import com.example.pagingbasic.databinding.ActivityFlowBinding
import com.example.pagingbasic.paging.PostViewModel
import com.example.pagingbasic.view.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FlowActivity : BaseActivity<ActivityFlowBinding>(R.layout.activity_flow) {

    private val viewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bind {
            val adapter = PostAdapter()
            recyclerView.adapter = adapter
            recyclerView.setHasFixedSize(true)

            lifecycleScope.launch {
                viewModel.getPosts().collectLatest { adapter.submitData(it) }
            }
        }
    }
}