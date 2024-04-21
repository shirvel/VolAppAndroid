package com.example.app

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class UpdateUserProfile : AppCompatActivity() {

    var nameTextField: EditText? = null
    var emailTextField: EditText? = null
    var passwordTextField: EditText? = null

    var saveButton: Button? = null
    var cancelButton: Button? = null

    private lateinit var imageView: ImageView
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContentView(R.layout.activity_update_user_profile)


        setUpUI()

    }

    private fun setUpUI() {
        nameTextField = findViewById(R.id.editTextUserName)
        emailTextField = findViewById(R.id.editTextEmailAddress)
        passwordTextField = findViewById(R.id.editTextPassword)

        saveButton = findViewById(R.id.btnSave)
        cancelButton = findViewById(R.id.btnCancel)

        imageView = findViewById(R.id.imageViewAvatar)



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
            val name = nameTextField?.text.toString();
            val email = emailTextField?.text.toString();
            val password = passwordTextField?.text.toString();

            val intent = Intent(this@UpdateUserProfile, PostsList::class.java)

            startActivity(intent)

        }

    }

    private fun clickCancelButton() {
        cancelButton?.setOnClickListener {
            finish()
        }
    }

    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    // Override onActivityResult to handle the result of the image selection
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val uri: Uri = data.data!!
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