package com.example.app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import java.util.logging.Handler

class UpdateUserProfileFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null

    private var saveButton: Button? = null
    private var cancelButton: Button? = null

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
        // Load user profile photo
        loadProfilePhoto()
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

           // user.photoUrl.se

            // Disable editing for the email field
            emailTextField?.isEnabled = false
        } else {
            emailTextField?.setText("<not logged in>")
        }

        clickToAddPhoto()
        clickSaveButton()
        clickCancelButton()
    }

    private fun clickToAddPhoto() {
        imageView.setOnClickListener {
            chooseImage()
        }
    }

    private fun clickSaveButton() {
        saveButton?.setOnClickListener {
            val user = Firebase.auth.currentUser
            user?.let {
                val newPassword = passwordTextField?.text.toString()

                if (newPassword.isEmpty()) {
                    showToast("Password field can't be empty")
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

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri: Uri = data.data!!

            // Obtain the contentResolver from the Fragment's Context
            val contentResolver = requireContext().contentResolver

            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
            val circularBitmap = getCircularBitmap(bitmap)
            imageView.setImageBitmap(circularBitmap)
        }
    }

    private fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val outputBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = android.graphics.Canvas(outputBitmap)

        val color = -0xbdbdbe
        val paint = android.graphics.Paint()
        val rect = android.graphics.Rect(0, 0, bitmap.width, bitmap.height)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = color
        canvas.drawCircle(bitmap.width / 2.toFloat(), bitmap.height / 2.toFloat(), bitmap.width / 2.toFloat(), paint)
        paint.xfermode = android.graphics.PorterDuffXfermode(android.graphics.PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return outputBitmap
    }

    private fun loadProfilePhoto() {
        val user = Firebase.auth.currentUser
        user?.photoUrl?.let { photoUrl ->
            Picasso.get()
                .load(photoUrl)
                .placeholder(R.drawable.circular_background) // Placeholder image while loading or if URL is null
                .error(R.drawable.circular_background) // Image to display if loading fails
                .into(imageView)
        }
    }

    private fun showNotLoggedInToast() {
        val toast = Toast.makeText(context, "You are not logged in", Toast.LENGTH_SHORT)
        toast.show()

        // Hide the toast after 5 seconds
    //    toast.cancelAfterDelay(5000)
    }

    private fun showToast(message: String) {
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast.show()

        // Hide the toast after 5 seconds
  //      toast.cancelAfterDelay(7000)
    }

//    private fun Toast.cancelAfterDelay(delay: Long) {
//        val handler = Handler()
//        handler.postDelayed({ this.cancel() }, delay)
//    }
}
