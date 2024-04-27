package com.example.app.Modules.Comments.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Comment
import com.example.app.Modules.Comments.Comments
import com.example.app.databinding.CommentRowBinding

class CommentAdapter(private var commentsList: MutableList<Comment>?) : RecyclerView.Adapter<CommentViewHolder>() {
    var listener: Comments.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CommentRowBinding.inflate(inflater, parent, false)
        return CommentViewHolder(binding.root, listener)
    }

    override fun getItemCount(): Int = commentsList?.size ?: 0

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = commentsList?.get(position)
        holder.bind(comment)
        holder.itemView.setOnClickListener {
            listener?.onItemClick(position)
        }
    }
}
