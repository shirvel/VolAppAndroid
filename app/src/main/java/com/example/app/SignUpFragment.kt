package com.example.app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.app.Modules.Posts.AllPosts
import com.example.app.model.User
import com.example.app.model.UserFirebaseModel
import com.example.app.model.UserListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null

    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private lateinit var imageView: ImageView
    private var selectedImageUri: Uri? = null
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>
    private val PICK_IMAGE_REQUEST = 1

    //private late init var authFragment: AuthFragment
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sign_up, container, false)
        setUpUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth
    }


    private fun setUpUI(view: View) {
        emailTextField = view.findViewById(R.id.editTextEmailAddress)
        passwordTextField = view.findViewById(R.id.editTextPassword)

        saveButton = view.findViewById(R.id.btnSave)
        cancelButton = view.findViewById(R.id.btnCancel)

        imageView = view.findViewById(R.id.imageViewAvatar)

        clickToAddPhoto();
        clickSaveButton(view);
        clickCancelButton();
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

            val id = 18; // TODO: change userID

            if (validateCreds(email, password)) {
                createAccount(email, password, view)
            }
        }
    }

    private fun validateCreds(email:String, password:String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "All fields must be filled",
                Toast.LENGTH_LONG,
            ).show()
            return false
        }
        else {
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

    fun createAccount(email: String, password: String, view: View) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    //   Log.i(TAG, "createUserWithEmail:success")
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        onSuccess(email, view, user)
                    }
                    else {
                        Toast.makeText(
                            requireContext(),
                            task.exception?.message ?: "User is null.",
                            Toast.LENGTH_LONG,
                        ).show()

                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message ?: "Authentication failed.",
                        Toast.LENGTH_LONG,
                    ).show()

                }
            }
    }

    fun onSuccess(email: String, view: View, currentUser: FirebaseUser) {
        val image = imageView?.toString() ?: ""
        // TODO maybe pass user as argument
        val user = User(currentUser.uid, email,  "placeHolderForImageUrl")
        UserListModel.instance.addUser(view, user) {
        }
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