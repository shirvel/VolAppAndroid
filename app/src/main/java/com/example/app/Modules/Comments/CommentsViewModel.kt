package com.example.app.Modules.Comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.app.model.Comment
import com.example.app.model.CommentListModel

class CommentViewModel: ViewModel() {
    var comments: LiveData<MutableList<Comment>>? = null
    // Method to get comments by post ID

}