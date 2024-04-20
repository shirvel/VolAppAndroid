package com.example.app.Modules.Posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import com.example.app.Model.Post
import com.example.app.R

class EditPost : Fragment() {
    private var contentTextField: EditText? = null
    private var idTextField: EditText? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null

    private lateinit var currentPost: Post // Assuming you pass the post to edit

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        contentTextField = view.findViewById(R.id.edit_post_content)
        saveButton = view.findViewById(R.id.save_edited_post_button)
        // Populate UI with current post content
        contentTextField?.setText(currentPost.content)

        saveButton?.setOnClickListener {
            val newContent = contentTextField?.text.toString()

            // Update post content
            currentPost.content = newContent

            // Save the updated post
            // Call  Model method to update the post here


        }

        cancelButton?.setOnClickListener {
            // Optionally, navigate back to previous fragment
            // requireActivity().supportFragmentManager.popBackStack()
        }
    }
}