package com.example.app.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.app.model.Post
import com.example.app.model.PostListModel
import com.example.app.Modules.Posts.PostsViewModel
import com.example.app.R
import com.example.app.databinding.FragmentEditPostBinding

class EditPost : Fragment() {

    private lateinit var viewModel: PostsViewModel
    private lateinit var binding: FragmentEditPostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_post, container, false
        )

        // Initialize ViewModel
        viewModel = ViewModelProvider(this).get(PostsViewModel::class.java)

        // Observe current post details

        val postId = arguments?.let { args ->
            PostArgs.fromBundle(args).postWriter
        }

        // Fetch post details using post ID
        postId?.let { postId ->
            Log.i("TAG", "postid from edit $postId")
            val postLiveData = PostListModel.instance.getPostById(postId)
            postLiveData.observe(viewLifecycleOwner) { post ->
                // This block will be executed when the LiveData has a non-null value
                if (post != null) {
                    // Post is not null, handle it here
                    Log.i("TAG", "editpost $post")
                    viewModel.setCurrentPost(post) // Set current post in ViewModel
                    updateUI(post)
                } else {
                    // Post is null, handle this case
                    Log.e("TAG", "Post with ID $postId not found.")
                    // You might want to display an error message or handle this case differently
                }
            }
        }

        // Set click listener for save button
        binding.btnSave.setOnClickListener {
            // Extract updated post details from UI
            val updatedPost = getUpdatedPostFromUI(postId ?:"")
            Log.i("TAG", "the post after edit $updatedPost")
            PostListModel.instance.addPost(updatedPost) {
                val navController = Navigation.findNavController(requireView())
                navController.navigate(R.id.allPost)
            }
            // Update post using ViewModel
            //viewModel.updatePost(updatedPost)
            // Navigate back or perform any other action
            // (e.g., using Navigation Component)
        }

        // Set click listener for cancel button
        binding.btnCancel.setOnClickListener {
            // Navigate back or perform any other action
            // (e.g., using Navigation Component)
        }

        return binding.root
    }

    private fun updateUI(post: Post) {
        binding.etTitle.setText(post.title)
        binding.etContent.setText(post.content)

    }

    private fun getUpdatedPostFromUI(postId: String): Post {
        // Extract updated post details from EditTexts
        // and return as a Post object
        val updatedTitle = binding.etTitle.text.toString()
        val updatedContent = binding.etContent.text.toString()
        val updatedLocation = binding.etLocation.text.toString()

        return Post(postId, updatedTitle, updatedContent, "", updatedLocation,false)
    }
}
