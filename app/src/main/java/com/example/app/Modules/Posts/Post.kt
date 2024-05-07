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
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Post : Fragment() {
    private var idTextView: TextView? = null
    private var titleTextView: TextView? = null
    private var contentTextView: TextView? = null
    private var locationTextView: TextView? = null
    private var toCommentsButton: Button? = null
    private var btnToAddComments: Button? = null
    private var imageView: ImageView? = null
    private var writerTextView: TextView? = null
    private var btnDelete: Button? = null
    private var btnEdit: Button? = null

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
        writerTextView = view.findViewById(R.id.tvPostWriter)
        toCommentsButton = view.findViewById(R.id.btnToComments)
        btnToAddComments = view.findViewById(R.id.btnToAddComments)
        btnDelete = view.findViewById<Button>(R.id.btnToDeletePost)
        btnEdit = view.findViewById<Button>(R.id.btnToEditPost)

        arguments?.let {
            idTextView?.text = PostArgs.fromBundle(it).postId
            val postLiveData = getTheCurrentPost(PostArgs.fromBundle(it).postId)
            postLiveData.observe(viewLifecycleOwner, Observer { post ->
                // Update UI with the post data
                titleTextView?.text = post.title
                contentTextView?.text = post.content
                locationTextView?.text = post.address
                writerTextView?.text = post.writer
                Log.d("PostFragment", "Image URL: ${post.image}")
                Glide.with(requireContext())
                    .load(post.image) // Replace "post.imageUrl" with the actual URL of the image
                    .into(imageView!!)

                val writer = Firebase.auth.currentUser?.uid.toString()
                Log.d("PostFragment", "writer from posts: ${writer}")
                Log.d("PostFragment", "post.writer from posts: ${post.writer}")

                if (post.writer == writer){
                    btnDelete?.visibility = View.VISIBLE
                    btnEdit?.visibility = View.VISIBLE
                }else {
                    btnDelete?.visibility = View.GONE
                    btnEdit?.visibility = View.GONE
                }
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
        btnEdit?.setOnClickListener {
            arguments?.let { args ->
                val postId = PostArgs.fromBundle(args).postId
                val action = PostDirections.actionPostToEdit(postId)
                findNavController().navigate(action)
            }
        }
        btnDelete?.setOnClickListener {
            arguments?.let { args ->
                val postId = PostArgs.fromBundle(args).postId
                val action = PostDirections.actionPostToDelete(postId)
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
