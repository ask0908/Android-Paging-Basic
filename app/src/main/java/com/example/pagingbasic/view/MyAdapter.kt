package com.example.pagingbasic.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pagingbasic.data.model.Post
import com.example.pagingbasic.databinding.ItemLayoutBinding
import timber.log.Timber

class MyAdapter: PagingDataAdapter<Post, MyAdapter.MyViewHolder>(diffUtil) {
    inner class MyViewHolder(
        private val binding: ItemLayoutBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post) {
            Timber.e("## bind : ${post.id} 바인드됨!!")
            binding.run {
                userIdText.text = post.myUserId.toString()
                idText.text = post.id.toString()
                titleText.text = post.title
                bodyText.text = post.body
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = getItem(position)

        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<Post>() {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean =
                oldItem == newItem

        }
    }
}