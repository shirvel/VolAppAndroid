package com.example.app.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.firestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.memoryCacheSettings
import com.google.firebase.ktx.Firebase
import com.google.firebase.Timestamp
class PostFirebaseModel {

    private val firestoreDB = Firebase.firestore

    companion object {
        const val POSTS_COLLECTION_PATH = "posts"
    }

    init {
        val settings = firestoreSettings {
            setLocalCacheSettings(memoryCacheSettings { })

        }
        firestoreDB.firestoreSettings = settings
    }


    fun getAllPosts(since: Long, callback: (List<Post>) -> Unit) {

        firestoreDB.collection(POSTS_COLLECTION_PATH)
            .whereGreaterThanOrEqualTo(Post.LAST_UPDATED, Timestamp(since, 0))
            .get().addOnCompleteListener {
                when (it.isSuccessful) {
                    true -> {
                        val posts: MutableList<Post> = mutableListOf()
                        for (json in it.result) {
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

    fun deletePost(postId: String, callback: () -> Unit) {
        firestoreDB.collection(POSTS_COLLECTION_PATH)
            .document(postId).delete().addOnSuccessListener {
                callback()
            }
    }

    fun getPostsByUser(userId: String): LiveData<MutableList<Post>> {
        val resultLiveData = MutableLiveData<MutableList<Post>>()
        firestoreDB.collection(POSTS_COLLECTION_PATH).whereEqualTo("writer", userId)
            .get()
            .addOnSuccessListener { documents ->
                val posts = mutableListOf<Post>()
                for (document in documents) {
                    val post = document.toObject(Post::class.java)
                    Log.e("PostRepository", "posts from firebase:  ${post}")
                    posts.add(post)
                }
                resultLiveData.value = posts
            }
            .addOnFailureListener { exception ->
                // Handle failure
                Log.e("PostRepository", "Error getting posts: ", exception)
                resultLiveData.value = mutableListOf() // Empty list on failure
            }
        return resultLiveData
    }

}