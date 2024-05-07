package com.example.app.Modules.Comments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import com.example.app.Modules.Posts.PostArgs
import com.example.app.R
import com.example.app.databinding.FragmentAddCommentBinding
import com.example.app.model.Comment
import com.example.app.model.CommentListModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
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
            btnAddPostCancel.setOnClickListener {
                // Assuming you're inside a Fragment
                view?.findNavController()?.popBackStack(R.id.post, false)

            }

            btnAddPostSave.setOnClickListener {
                arguments?.let {
                    val postId = PostArgs.fromBundle(it).postId
                    val content = etAddPostComment.text.toString()
                    val commentId = UUID.randomUUID().toString()
                    val writer = Firebase.auth.currentUser.toString()
                    if (writer != null) {
                        //Uncomment the following lines to add comment functionality
                        val comment = Comment(commentId, writer, content, postId)
                        CommentListModel.instance.addComment(comment) {
                            view?.findNavController()?.popBackStack(R.id.post, false)
                            Toast.makeText(
                                requireContext(),
                                "The post added successfully",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
    }
}
