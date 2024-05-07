package com.example.app.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.app.R
import com.example.app.model.PostListModel

class Post : Fragment() {
    private var idTextView: TextView? = null
    private var titleTextView: TextView? = null
    private var contentTextView: TextView? = null
    private var locationTextView: TextView? = null
    private var toCommentsButton: Button? = null
    private var btnToAddComments: Button? = null
    private var imageView: ImageView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_post, container, false)
        idTextView = view.findViewById(R.id.tvPostId)
        titleTextView = view.findViewById(R.id.tvPostTitle)
        contentTextView = view.findViewById(R.id.tvPostContent)
        locationTextView = view.findViewById(R.id.tvPostLocation)
        imageView = view.findViewById(R.id.postImageView)
        toCommentsButton = view.findViewById(R.id.btnToComments)
        btnToAddComments = view.findViewById(R.id.btnToAddComments)

        arguments?.let {
            idTextView?.text = PostArgs.fromBundle(it).postId
            val postLiveData = getTheCurrentPost(PostArgs.fromBundle(it).postId)
            postLiveData.observe(viewLifecycleOwner, Observer { post ->
                // Update UI with the post data
                titleTextView?.text = post.title
                contentTextView?.text = post.content
                locationTextView?.text = post.address
                Log.d("PostFragment", "Image URL: ${post.image}")
                Glide.with(requireContext())
                    .load(post.image) // Replace "post.imageUrl" with the actual URL of the image
                    .into(imageView!!)
            })
        }

        //val action = Navigation.createNavigateOnClickListener(R.id.action_post_to_comments)
        toCommentsButton?.setOnClickListener{
            arguments?.let {args ->
                val postId = PostArgs.fromBundle(args).postId
                val action = PostDirections.actionPostToComments(postId)
                findNavController().navigate(action)
            }
        }

        btnToAddComments?.setOnClickListener {
            arguments?.let { args ->

                val postId = PostArgs.fromBundle(args).postId
                val action = PostDirections.actionPostToAddComment(postId)
                findNavController().navigate(action)
            }
        }

        return view
    }

    private fun getTheCurrentPost(postId: String): LiveData<com.example.app.model.Post> {
        // Fetch the post by ID using Room DAO or any other method
        val postLiveData: LiveData<com.example.app.model.Post> =
            PostListModel.instance.getPostById(postId)

        // Return the LiveData object
        return postLiveData
    }
}
