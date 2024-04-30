package com.example.app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.app.Modules.Posts.AllPosts
import com.example.app.model.User
import com.example.app.model.UserFirebaseModel
import com.example.app.model.UserListModel

class SignUpFragment : Fragment() {

    private var nameTextField: EditText? = null
    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null

    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_sign_up, container, false)
        setUpUI(view)
        return view
    }


    private fun setUpUI(view: View) {
        nameTextField = view.findViewById(R.id.editTextUserName)
        emailTextField = view.findViewById(R.id.editTextEmailAddress)
        passwordTextField = view.findViewById(R.id.editTextPassword)

        saveButton = view.findViewById(R.id.btnSave)
        cancelButton = view.findViewById(R.id.btnCancel)

        imageView = view.findViewById(R.id.imageViewAvatar)



        clickToAddPhoto();
        clickSaveButton();
        clickCancelButton();


    }

    private fun clickToAddPhoto() {
        imageView.setOnClickListener {
            chooseImage()
        }

    }


    private fun clickSaveButton() {
        saveButton?.setOnClickListener {
            val name = nameTextField?.text.toString()
            val email = emailTextField?.text.toString()
            val password = passwordTextField?.text.toString()

            val id = 5; // TODO: change userID

            val user = User(id, email, password, name)
            UserListModel.instance.addUser(user) {
//                Navigation.findNavController(it).popBackStack(R.id.LogginFragment, false)
            }
        }
    }


    private fun clickCancelButton() {
        cancelButton?.setOnClickListener {
         //   activity?.finish() //TODO?
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


}