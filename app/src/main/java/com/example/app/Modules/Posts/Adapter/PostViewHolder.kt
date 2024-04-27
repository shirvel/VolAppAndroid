package com.example.app.Modules.Posts.Adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Post
import com.example.app.Modules.Posts.AllPosts
import com.example.app.databinding.PostRowBinding

class PostViewHolder(private val binding: PostRowBinding, private val listener: AllPosts.OnItemClickListener?) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post?) {
        binding.post = post
        binding.executePendingBindings()

        binding.root.setOnClickListener {
            listener?.onItemClick(adapterPosition)
        }
    }
}
