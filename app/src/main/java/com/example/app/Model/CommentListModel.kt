package com.example.app.Model

class CommentListModel private constructor(){
    val comments: MutableList<Comment> = ArrayList()
    companion object{
        val instance: CommentListModel = CommentListModel()
    }

    init {
        for (i in 0..20){
            val comment = Comment("writer: $i", "content: $i", 123)
            comments.add(comment)
        }
    }
}