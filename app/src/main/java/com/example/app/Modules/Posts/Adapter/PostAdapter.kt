package com.example.app.Modules.Posts.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Model.Post
import com.example.app.Modules.Posts.AllPosts
import com.example.app.R

class AllPostsAdapter(var postsList: MutableList<Post>?): RecyclerView.Adapter<PostViewHolder>() {
    var listener: AllPosts.OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
        return PostViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = postsList?.size ?: 0

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        // Here we put all the data itself.
        val post = postsList?.get(position)
        holder.bind(post)
    }

}