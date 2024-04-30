package com.example.app.Modules.Comments.Adapter

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Comment
import  com.example.app.Modules.Comments.Comments
import com.example.app.Modules.Comments.CommentsRcyclerViewActivity
import com.example.app.R
import com.example.app.model.Post

class CommentViewHolder(val itemView: View,
                        val listener: CommentsRcyclerViewActivity.OnItemClickListener?,
                        var comments: List<Comment>?): RecyclerView.ViewHolder(itemView) {

    var writerTextView: TextView? = null
    var contentTextView: TextView? = null
   // var postIdTextView: TextView? = null
    var comment: Comment? = null

    init {
        writerTextView = itemView.findViewById(R.id.tvWriterName)
        contentTextView = itemView.findViewById(R.id.tvCommentContent)
       // postIdTextView = itemView.findViewById(R.id.tvPostListRowContent)
        itemView.setOnClickListener {
            Log.i("TAG", "StudentViewHolder: Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onCommentClicked(comment)
        }
    }

    fun bind(comment: Comment?) {
        this.comment = comment
        writerTextView?.text = comment?.writer
        contentTextView?.text = comment?.content
    }
}
