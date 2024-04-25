package com.example.app.Model

class PostListModel private constructor(){
    val posts: MutableList<Post> = ArrayList()

    companion object {
        val instance: PostListModel = PostListModel()
    }

    init {
        for (i in 0..20) {
            val post = Post(
                writer = "writer: $i",
                content = "content: $i",
                image = "https://me.com/avatar.jpg",
                isLiked = false
            )
            posts.add(post)
        }
    }
}
