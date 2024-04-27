package com.example.app.Modules.Comments.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.CommentRowBinding
import com.example.app.model.Comment
import  com.example.app.Modules.Comments.Comments

class CommentViewHolder(private val view: View, private val listener: Comments.OnItemClickListener?) : RecyclerView.ViewHolder(view) {

    fun bind(comment: Comment?) {
        val binding = CommentRowBinding.bind(view)
        binding.comment = comment
        binding.executePendingBindings()
    }
}
