package com.example.app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory

import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.Navigation
import com.example.app.model.User
import com.example.app.model.UserListModel
import com.bumptech.glide.Glide
import android.content.ContentResolver
import android.content.Context
import android.net.Uri

import com.google.firebase.firestore.ktx.firestore
import com.squareup.picasso.Picasso

private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

class UpdateUserProfileFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null

    private var saveButton: Button? = null
    private var cancelButton: Button? = null
    private lateinit var selectedImageUri: Uri
    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_update_user_profile, container, false)
        setUpUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Initialize image picker launcher
        imagePickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    val selectedImageUri = result.data?.data
                    Log.d("PostFragment", "Image User 1 $selectedImageUri")
                    selectedImageUri?.let {
                        // Update UI with selected image
                        imageView.setImageURI(it)
                        // Save selected image URI
                        this.selectedImageUri = it
                        Log.d("PostFragment", "Image User $selectedImageUri")
                    }
                }
            }
    }


    private fun setUpUI(view: View) {
        emailTextField = view.findViewById(R.id.editTextEmailAddress)
        passwordTextField = view.findViewById(R.id.editTextPassword)

        saveButton = view.findViewById(R.id.btnSave)
        cancelButton = view.findViewById(R.id.btnCancel)

        imageView = view.findViewById(R.id.imageViewAvatar)

        val user = Firebase.auth.currentUser
        if (user != null) {
            emailTextField?.setText(user.email)
            // A placeholder password
            passwordTextField?.setText("123456")

            // Disable editing for the email field
            emailTextField?.isEnabled = false
            // Load current profile photo using Glide
            val userEmail: String = user.email ?: ""
            UserListModel.instance.getUserImageByEmail(
                userEmail, // Replace with the actual email
                onSuccess = { imageUrl ->
                    // This block will be executed if the operation is successful
                    if (imageUrl != null) {
                        // Use the imageUrl to load the user's image
                        // For example, you can use Glide to load the image into an ImageView

                        Picasso.get().load(imageUrl).into(imageView)
                    } else {
                        // Handle case where imageUrl is null (user has no image)
                    }
                },
                onFailure = { errorMessage ->
                    // This block will be executed if the operation encounters an error
                    Log.e("TAG", "Failed to get user image: $errorMessage")
                }
            )

        } else {
            emailTextField?.setText("<not logged in>")

        }

        clickToAddPhoto();
        clickSaveButton();
        clickCancelButton();
    }


    private fun clickToAddPhoto() {
        imageView.setOnClickListener {
            // Launch image picker
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            imagePickerLauncher.launch(Intent.createChooser(intent, "Select Picture"))
        }
    }


    private fun clickSaveButton() {
        saveButton?.setOnClickListener {
            val user = Firebase.auth.currentUser
            user?.let {
                val newPassword = passwordTextField?.text.toString()

                if (newPassword.isEmpty()) {
                    showToast("Password field can't be empty")
                } else if (selectedImageUri != Uri.EMPTY) {
                    val imageUrl = selectedImageUri?.toString() ?: ""
                    var userId = user.toString()
                    var email = emailTextField?.text.toString()
                    val user = User(userId, email, imageUrl)
                    Log.d("ImageFragment", "Image: $user")
                    UserListModel.instance.addUser(requireView(), user) {
                        Navigation.findNavController(requireView()).navigate(R.id.allPost)
                        Toast.makeText(
                            requireContext(),
                            "The user image changed successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    user.updatePassword(newPassword)
                        .addOnCompleteListener { passwordUpdateTask ->
                            if (passwordUpdateTask.isSuccessful) {
                                // Password updated successfully
                                findNavController().navigate(R.id.allPost)
                                showToast("Your password was changed successfully")
                            } else {
                                // Password update failed
                                showToast("Password update failed. Please make sure to enter 6 characters")
                            }
                        }

                }
            } ?: run {
                // User is not logged in
                showNotLoggedInToast()
            }

        }
    }


    private fun clickCancelButton() {
        cancelButton?.setOnClickListener {
            findNavController().navigate(R.id.action_updateUserProfile_to_allPosts)
        }
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()

        // Hide the toast after 5 seconds
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ toast.cancel() }, 7000)
    }

    private fun showNotLoggedInToast() {
        val toast = Toast.makeText(context, "You are not logged in", Toast.LENGTH_SHORT)
        toast.show()

        // Hide the toast after 5 seconds
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ toast.cancel() }, 5000)
    }

    fun getContentResolverForStringUri(context: Context?, uriString: String): ContentResolver? {
        return context?.let {
            try {
                val uri = Uri.parse(uriString)
                it.contentResolver
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}