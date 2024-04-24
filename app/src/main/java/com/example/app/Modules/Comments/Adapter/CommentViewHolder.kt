package com.example.app.Modules.Comments.Adapter

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Model.Comment
import com.example.app.Modules.Comments.Comments
import com.example.app.R

class CommentViewHolder(val itemView: View, val listener: Comments.OnItemClickListener?): RecyclerView.ViewHolder(itemView) {
    var writerTextView: TextView? = null
    var contentTextView: TextView? = null
    var comment: Comment? = null

    init {
        writerTextView = itemView.findViewById(R.id.tvWriterName)
        contentTextView = itemView.findViewById(R.id.tvCommentContent)

        itemView.setOnClickListener {
            listener?.onItemClick(adapterPosition)
        }
    }

    fun bind(comment: Comment?){
        this.comment = comment
        writerTextView?.text = comment?.writer
        contentTextView?.text = comment?.content
    }

}