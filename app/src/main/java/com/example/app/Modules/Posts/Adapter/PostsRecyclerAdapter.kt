package com.example.app.Modules.Posts.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Modules.Posts.AllPosts
import com.example.app.model.Post
import com.example.app.R

class PostsRecyclerAdapter(var posts: List<Post>?, var isInConnectedUserFragment: Boolean): RecyclerView.Adapter<PostViewHolder>() {

    var listener: AllPosts.OnItemClickListener? = null
    override fun getItemCount(): Int = posts?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        var row = R.layout.post_row_without_edit
        if (isInConnectedUserFragment){
            row = R.layout.post_row
        }
        val itemView = LayoutInflater.from(parent.context).inflate(row, parent, false)
        return PostViewHolder(itemView, listener, posts)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = posts?.get(position)
        holder.bind(post)

    }
}