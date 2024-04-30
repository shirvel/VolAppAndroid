package com.example.app.model

import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase

class PostFirebaseModel {

    private val firestoreDB = Firebase.firestore
    companion object {
        const val POSTS_COLLECTION_PATH = "posts"
    }
    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings {  })

        }
        firestoreDB.firestoreSettings = settings
    }


    fun getAllPosts(callback: (List<Post>) -> Unit) {
        firestoreDB.collection(POSTS_COLLECTION_PATH).get().addOnCompleteListener() {
            when (it.isSuccessful) {
                true -> {
                    val posts: MutableList<Post> = mutableListOf()
                    for (json in it.result){
                        val post = Post.fromJSON(json.data)
                        posts.add(post)
                    }
                    callback(posts)

                }
                false -> callback(listOf())
            }
        }
    }

    fun addPost(post: Post, callback: () -> Unit) {
        // Add the post to the "posts" collection
        firestoreDB.collection(POSTS_COLLECTION_PATH)
            .document(post.postId).set(post.json).addOnSuccessListener {
                callback()
            }
    }
}