package com.example.app.Modules.Posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.app.R



class Post : Fragment() {
    var idTextView: TextView? = null
    var titleTextView: TextView? = null
    var contentTextView: TextView? = null
    var locationTextView: TextView? = null
    var toCommentsButton: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        idTextView = view.findViewById(R.id.tvPostId)
        titleTextView = view.findViewById(R.id.tvPostTitle)
        contentTextView = view.findViewById(R.id.tvPostContent)
        locationTextView = view.findViewById(R.id.tvPostLocation)
        toCommentsButton = view.findViewById(R.id.btnToComments)

        arguments?.let{
            idTextView?.text = PostArgs.fromBundle(it).postWriter
            val post = getTheCurrentPost(PostArgs.fromBundle(it).postWriter)
            titleTextView?.text = post.title
            contentTextView?.text = post.content
            locationTextView?.text = post.address


        }

        val action = Navigation.createNavigateOnClickListener(R.id.action_post_to_comments)
        toCommentsButton?.setOnClickListener(action)

        return view


    }


    fun getTheCurrentPost(postId: String): com.example.app.model.Post{
        // TODO: Implement the get post by Id
        return com.example.app.model.Post(postId, "writer","content", "" , "","test address")
    }

}