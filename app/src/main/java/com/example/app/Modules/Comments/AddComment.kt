package com.example.app.Modules.Posts

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.app.R
import com.example.app.databinding.FragmentAddCommentBinding
import com.example.app.model.Comment
import com.example.app.model.CommentListModel
import com.example.app.model.Post
import java.util.UUID

class AddComment : Fragment() {

    private var _binding: FragmentAddCommentBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddCommentBinding.inflate(inflater, container, false)
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
            btnAddCommentCancel.setOnClickListener {
                val navController = Navigation.findNavController(view)
                navController.navigate(R.id.allPost)
            }

            btnAddCommentSave.setOnClickListener {
                val postId = arguments?.let { args ->
                    PostArgs.fromBundle(args).postWriter
                } ?: ""

                val content = etAddCommentContent.text.toString()
                val commentId = UUID.randomUUID().toString()
                val comment = Comment(commentId, "", content, postId)
                CommentListModel.instance.addComment(comment) {
                    Toast.makeText(
                        requireContext(),
                        "The comment added successfully",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }
}
