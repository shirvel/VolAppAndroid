package com.example.app.Modules.Posts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.app.model.Post

class PostsViewModel: ViewModel() {
    private val _currentPost = MutableLiveData<Post>()
    val currentPost: LiveData<Post>
        get() = _currentPost

    var posts: LiveData<MutableList<Post>>? = null
    // Function to fetch the current post details from the repository
    // Function to update current post
    fun setCurrentPost(post: Post) {
        _currentPost.value = post
    }

}