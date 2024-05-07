package com.example.app.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.example.app.databinding.FragmentPostBinding
import com.example.app.model.PostListModel
import com.example.app.model.Post


class Post : Fragment() {
    private lateinit var binding: FragmentPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)

        // Retrieve postId from arguments
        val postId = arguments?.let { PostArgs.fromBundle(it).postId } ?: ""

        // Fetch post data
        val postLiveData = getPostById(postId)

        // Observe the LiveData
        postLiveData.observe(viewLifecycleOwner) { post ->
            // Set each property individually
            binding.tvPostTitle.text = post.title
            binding.tvPostContent.text = post.content
            binding.tvPostLocation.text = post.address
            binding.tvPostId.text = post.postId
        }

        // Set click listener for the "To Comments" button
        binding.btnToComments.setOnClickListener {
            val action = PostDirections.actionPost1ToComments(postId)
            Navigation.findNavController(it).navigate(action)
        }
        // Set click listener for the "To Comments" button
        binding.btnToAddComments?.setOnClickListener {
            arguments?.let { args ->

                val postId = PostArgs.fromBundle(args).postId
                Log.i("TAG", "postId from post $postId")
                val action = PostConnectedUserDirections.actionPostToEdit(postId)
                findNavController().navigate(action)
            }
        }

        return binding.root
    }

    private fun getPostById(postId: String): LiveData<Post> {
        // Fetch the post by ID using Room DAO
        return PostListModel.instance.getPostById(postId)
    }
}
