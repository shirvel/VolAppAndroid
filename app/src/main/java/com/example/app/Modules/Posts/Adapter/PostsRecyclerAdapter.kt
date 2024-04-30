package com.example.app.Modules.Posts.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Post
import com.example.app.Modules.Posts.PostsRcyclerViewActivity
import com.example.app.R

class PostsRecyclerAdapter(var posts: List<Post>?): RecyclerView.Adapter<PostViewHolder>() {

    var listener: PostsRcyclerViewActivity.OnItemClickListener? = null

    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return PostViewHolder(itemView, listener, posts)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts?.get(position)
        holder.bind(post)
    }
}