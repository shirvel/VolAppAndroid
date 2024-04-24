package com.example.app.Modules.Posts.Adapter

import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Model.Post
import com.example.app.Modules.Posts.AllPosts
import com.example.app.R

class PostViewHolder(val itemView: View, val listener: AllPosts.OnItemClickListener?): RecyclerView.ViewHolder(itemView){
    var writerTextView: TextView? = null
    var contentTextView: TextView? = null
    var post: Post? = null

    init {
        writerTextView = itemView.findViewById(R.id.tvPostListRowWriter)
        contentTextView = itemView.findViewById(R.id.tvPostListRowContent)

        itemView.setOnClickListener {
            Log.i("TAG", "View Holder: Posisiotn clicked $adapterPosition")
            listener?.onItemClick(adapterPosition)
        }
    }

    fun bind(post: Post?){
        this.post = post
        writerTextView?.text = post?.writer
        contentTextView?.text = post?.content
    }
}