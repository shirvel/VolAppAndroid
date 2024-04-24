package com.example.app.Model

class PostListModel private constructor(){
    val posts: MutableList<Post> = ArrayList()
    companion object{
        val instance: PostListModel = PostListModel()
    }

    init {
        for (i in 0..20){
            val post = Post("writer: $i", "content: $i", "",123)
            posts.add(post)
        }
    }
}