package com.example.app.model

import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class CommentFirebaseModel {
    private val firestoreDB = Firebase.firestore
    companion object {
        const val COMMENTS_COLLECTION_PATH = "comments"
    }
    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })

        }
        firestoreDB.firestoreSettings = settings
    }


    fun getAllComments(callback: (List<Comment>) -> Unit) {
        firestoreDB.collection(COMMENTS_COLLECTION_PATH).get().addOnCompleteListener() {
            when (it.isSuccessful) {
                true -> {
                    val comments: MutableList<Comment> = mutableListOf()
                    for (json in it.result){
                        val comment = Comment.fromJSON(json.data)
                        comments.add(comment)
                    }
                    callback(comments)

                }
                false -> callback(listOf())
            }
        }
    }

    fun addComment(comment: Comment, callback: () -> Unit) {
        // Add the comment to the "comments" collection
        firestoreDB.collection(COMMENTS_COLLECTION_PATH)
            .document(comment.commentId).set(comment.json).addOnSuccessListener {
                callback()
            }
    }
}