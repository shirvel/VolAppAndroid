package com.example.app.Modules.Posts
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.example.app.model.PostListModel
import com.example.app.model.Post
import com.example.app.Modules.Posts.AllPosts
import com.example.app.R
import com.example.app.databinding.FragmentAddPostBinding
import androidx.navigation.NavController
import java.util.UUID





class AddPost : Fragment() {

    private var _binding: FragmentAddPostBinding? = null
    private val binding get() = _binding!!

    private var selectedImageUri: Uri? = null
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.i("TAG", "in the add post create view")
        _binding = FragmentAddPostBinding.inflate(inflater, container, false)
        val view = binding.root
        setupUI(view)
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupUI(view: View) {
        binding.apply {
            btnAddPostCancel.setOnClickListener {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.allPost)
            }

            btnAddPostSave.setOnClickListener {
                val name = etAddPostTitle.text.toString()
                val content = etAddPostContent.text.toString()
                val image = selectedImageUri?.toString() ?: ""
                val postId = UUID.randomUUID().toString()

                println("image: $image")
                Log.i("TAG", "image $image")
                val location = etAddPostLocation.text.toString()

                Log.i("TAG", "image $image")

                val post = Post(postId, name, "", content, image,location)
                post.image = image
                PostListModel.instance.addPost(post) {
                // Navigation.findNavController(view).navigate(R.id.allPost)
                    Toast.makeText(
                        requireContext(),
                        "The post added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            btnSelectImage.setOnClickListener {
                openImagePicker()
            }
        }

        // Initialize image picker launcher
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
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }
}