package com.example.app.Modules.Posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.app.model.Post

class PostsViewModel: ViewModel() {
    var posts: LiveData<MutableList<Post>>? = null
}