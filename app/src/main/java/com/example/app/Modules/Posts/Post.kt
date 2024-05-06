package com.example.app.Modules.Posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.example.app.R
import com.example.app.databinding.FragmentPostBinding
import com.example.app.model.PostListModel


class Post : Fragment() {
    private lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        // Retrieve postId from arguments
        val postId = arguments?.let { PostArgs.fromBundle(it).postWriter } ?: ""

        // Fetch post data
        val post = getTheCurrentPost(postId)

        // Set post data to the binding object
        binding.post = post

        // Set click listener for the "To Comments" button
        binding.btnToComments.setOnClickListener {
            val action = PostDirections.actionPostToComments()
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }

    private fun getTheCurrentPost(postId: String): com.example.app.model.Post {
        // TODO: Implement the method to fetch the post by ID
        val post = PostListModel.instance.getPostById(postId)
        return post
    }
}
