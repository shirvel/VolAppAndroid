package com.example.app.Modules.Posts

import android.app.Activity
import android.net.Uri
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
import android.content.Intent
import android.content.pm.PackageManager
import android.provider.MediaStore
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class EditPost : Fragment() {

    private lateinit var viewModel: PostsViewModel
    private lateinit var binding: FragmentEditPostBinding
    private var selectedImageUri: Uri? = null
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

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
            PostArgs.fromBundle(args).postId
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

        // Set up image picker launcher
        imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedImageUri = result.data?.data
                selectedImageUri?.let {
                    // Update UI with selected image
                    binding.ivSelectedImage.setImageURI(it)
                    // Save selected image URI
                    this.selectedImageUri = it
                    Log.i("TAG", "Selected image URI: $it")
                }
            }
        }

        // Set click listener for select image button
        binding.btnSelectImage.setOnClickListener {
            openImagePicker()
        }

        // Set click listener for save button
        binding.btnSave.setOnClickListener {
            // Extract updated post details from UI
            val postId = postId ?: ""
            //val updatedPost = getUpdatedPostFromUI(postId ?: "")
            val imageUri = binding.ivSelectedImage.tag as? Uri
            Log.i("TAG", "th eimage: $imageUri")
            val updatedImage = selectedImageUri?.toString() ?: ""
            Log.i("TAG", "th eimage afetr sting: $updatedImage")
            val updatedTitle = binding.etTitle.text.toString()
            val updatedContent = binding.etContent.text.toString()
            val updatedLocation = binding.etLocation.text.toString()
            //return Post(postId, updatedTitle, updatedContent, "", updatedLocation,false)
            val updatedPost = Post(postId,updatedTitle,"",updatedContent,updatedImage,updatedLocation)
            updatedPost.image = updatedImage
            PostListModel.instance.addPost(updatedPost) {
                val navController = Navigation.findNavController(requireView())
                navController.navigate(R.id.allPost)
            }
        }

        binding.btnCancel.setOnClickListener {
            val navController = Navigation.findNavController(requireView())
            navController.navigate(R.id.allPost)
        }
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                REQUEST_CODE_STORAGE_PERMISSION
            )
        }

        return binding.root
    }

    private fun updateUI(post: Post) {
        binding.etTitle.setText(post.title)
        binding.etContent.setText(post.content)
        binding.etLocation.setText(post.address)
        //binding.ivSelectedImage.setImageURI(imageUri)
        Glide.with(this)
            .load(post.image)
            .into(binding.ivSelectedImage)
        Log.i("TAG", "omage url ${post.image}")

        //Picasso.get().load(post.image).into(binding.ivSelectedImage)
        //val imageUri = Uri.parse(post.image)
        //Glide.with(this).load(imageUri).into(binding.ivSelectedImage)

    }

    //private fun getUpdatedPostFromUI(postId: String): Post {
    // Extract updated post details from EditTexts
    // and return as a Post object
    //  val imageUri = binding.ivSelectedImage.tag as? Uri
    //val updatedImage = imageUri?.toString() ?: ""
    //val updatedTitle = binding.etTitle.text.toString()
    //val updatedContent = binding.etContent.text.toString()
    //val updatedLocation = binding.etLocation.text.toString()
    //return Post(postId, updatedTitle, updatedContent, "", updatedLocation,false)

    //return Post(postId,updatedTitle,"",updatedContent,updatedImage,false)
    //}

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
    companion object {
        private const val REQUEST_CODE_STORAGE_PERMISSION = 200
    }
}