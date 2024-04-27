package com.example.app.model

class CommentListModel  private constructor(){
    val comments: MutableList<Comment> = ArrayList()
    companion object {
        val instance: CommentListModel = CommentListModel()
    }
    init {
        for (i in 0..20) {
            val comment = Comment(writer = "writer: $i", content = "content: $i", postId = 123)
            comments.add(comment)
        }
    }
}
