package com.example.app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.MainActivity
import com.example.app.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.UUID

class SignUpFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null

    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 1

    private lateinit var auth: FirebaseAuth
    private lateinit var storage: FirebaseStorage
    private lateinit var storageReference: StorageReference
    private lateinit var firestore: FirebaseFirestore // Firestore instance

    private var imageUrl: String? = null // URL for the image

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        setUpUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth
        // Initialize Firebase Storage
        storage = FirebaseStorage.getInstance()
        storageReference = storage.reference
        // Initialize Firestore
        firestore = Firebase.firestore
    }

    private fun setUpUI(view: View) {
        emailTextField = view.findViewById(R.id.editTextEmailAddress)
        passwordTextField = view.findViewById(R.id.editTextPassword)

        saveButton = view.findViewById(R.id.btnSave)
        cancelButton = view.findViewById(R.id.btnCancel)

        imageView = view.findViewById(R.id.imageViewAvatar)

        clickToAddPhoto()
        clickSaveButton(view)
        clickCancelButton()
    }

    private fun clickToAddPhoto() {
        imageView.setOnClickListener {
            chooseImage()
        }
    }

    private fun clickSaveButton(view: View) {
        saveButton?.setOnClickListener {
            val email = emailTextField?.text.toString()
            val password = passwordTextField?.text.toString()

            if (validateCreds(email, password))
                createAccount(email, password)
        }
    }

    private fun validateCreds(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "All fields must be filled",
                Toast.LENGTH_LONG
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun clickCancelButton() {
        cancelButton?.setOnClickListener {
            findNavController().navigate(R.id.LoginFragment)
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
            uploadImageToFirebase(storage, uri)


            // Display the selected image in ImageView
            val contentResolver = requireContext().contentResolver
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
            val circularBitmap = getCircularBitmap(bitmap)
            imageView.setImageBitmap(circularBitmap)
        }
    }

    private fun uploadImageToFirebase(storage: FirebaseStorage, uri: Uri) {
        val ref = storage.reference.child("images/" + UUID.randomUUID().toString())
        ref.putFile(uri)
            .addOnSuccessListener { taskSnapshot ->
                // Image uploaded successfully, get the URL
                ref.downloadUrl.addOnSuccessListener { uri ->
                    imageUrl = uri.toString()
                    // Now you can save the imageUrl along with user data in Firestore
                    saveUserData(emailTextField?.text.toString(), passwordTextField?.text.toString(),
                        imageUrl!!
                    )
                }
            }
            .addOnFailureListener { e ->
                // Handle unsuccessful uploads
                Log.e(TAG, "Failed to upload image: $e")
            }
    }


    private fun saveUserData(email: String, password: String, imageUrl: String) {
        // Access a Cloud Firestore instance
        val db = FirebaseFirestore.getInstance()

        // Create a new user with a generated ID
        val user = hashMapOf(
            "email" to email,
            "password" to password,
            "imageUrl" to imageUrl // Assuming imageUrl is a global variable containing the URL of the uploaded image
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                // After saving user data, navigate to the desired destination
                findNavController().navigate(R.id.LoginFragment)
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
                Toast.makeText(
                    requireContext(),
                    "Failed to save user data.",
                    Toast.LENGTH_LONG
                ).show()
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

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    onSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message ?: "Authentication failed.",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun onSuccess(user: FirebaseUser?) {
        // TODO maybe pass user as argument
        findNavController().navigate(R.id.LoginFragment)
    }

    override fun onResume() {
        super.onResume()
        // Hide the bottom navigation menu
        if (activity != null && activity is MainActivity) {
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }
    }

    override fun onPause() {
        super.onPause()
        // Restore the bottom navigation menu visibility
        if (activity != null && activity is MainActivity) {
            (activity as MainActivity?)!!.showBottomNavigationView()
        }
    }

    companion object {
        private const val TAG = "Signup"
    }
}
