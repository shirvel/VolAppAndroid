package com.example.app.Modules.Comments.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Comment
import com.example.app.R
import com.example.app.Modules.Comments.Comments

class CommentsRecyclerAdapter(var comments: List<Comment>?): RecyclerView.Adapter<CommentViewHolder>() {

    var listener: Comments.OnItemClickListener? = null

    override fun getItemCount(): Int = comments?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.comment_row, parent, false)
        return CommentViewHolder(itemView, listener, comments)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments?.get(position)
        holder.bind(comment)
    }
}