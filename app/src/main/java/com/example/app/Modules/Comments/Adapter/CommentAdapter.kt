package com.example.app.Modules.Comments.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Model.Comment
import com.example.app.Modules.Comments.Comments
import com.example.app.R

class CommentAdapter(var commentsList: MutableList<Comment>?): RecyclerView.Adapter<CommentViewHolder>(){
    var listener: Comments.OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        return CommentViewHolder(itemView, listener)
    }

    override fun getItemCount(): Int = commentsList?.size ?: 0

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        // Here we put all the data itself.
        val comment = commentsList?.get(position)
        holder.bind(comment)
    }
}