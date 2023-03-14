package com.example.pagingbasic.paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingbasic.databinding.LoadStateBinding

class MyLoadStateAdapter(
    private val retry: () -> Unit
): LoadStateAdapter<MyLoadStateAdapter.LoadStateViewHolder>() {
    inner class LoadStateViewHolder(
        private val binding: LoadStateBinding
    ): RecyclerView.ViewHolder(binding.root) {
        init {
            // 버튼 클릭 시 프래그먼트에서 받아온 함수 호출
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            binding.run {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState !is LoadState.Loading
                errorText.isVisible = loadState !is LoadState.Loading
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): MyLoadStateAdapter.LoadStateViewHolder {
        val binding = LoadStateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LoadStateViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyLoadStateAdapter.LoadStateViewHolder,
        loadState: LoadState
    ) {
        holder.bind(loadState)
    }
}