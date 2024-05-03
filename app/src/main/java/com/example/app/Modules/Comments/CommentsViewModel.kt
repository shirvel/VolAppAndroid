package com.example.app.Modules.Comments

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.app.model.Comment

class CommentViewModel: ViewModel() {
    var comments: LiveData<MutableList<Comment>>? = null
}