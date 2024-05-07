package com.example.app.Modules.Posts
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.app.R
import com.example.app.model.Post
import com.example.app.model.PostListModel
import java.util.UUID
import androidx.navigation.fragment.findNavController
import androidx.lifecycle.observe



class PostConnectedUser: Fragment() {
    private lateinit var viewModel: PostsViewModel
    var writerTextView: TextView? = null
    var toCommentsButton: Button? = null
    var btnToDelete: Button? = null
    var btnToEdit: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fregment_post_user_connected, container, false)
        writerTextView = view.findViewById(R.id.tvPostId)
        toCommentsButton = view.findViewById(R.id.btnToComments)
        btnToDelete = view.findViewById(R.id.btnToDelete)
        btnToEdit = view.findViewById(R.id.btnToEdit)

        btnToDelete?.setOnClickListener {
            arguments?.let { args ->
                val postWriter = PostArgs.fromBundle(args).postId
                val action = PostConnectedUserDirections.actionPostToDelete(postWriter)
                findNavController().navigate(action)
            }
        }
        btnToEdit?.setOnClickListener {
            arguments?.let { args ->

                val postWriter = PostArgs.fromBundle(args).postId
                val action = PostConnectedUserDirections.actionPostToEdit(postWriter)
                findNavController().navigate(action)
            }
        }
        val action = Navigation.createNavigateOnClickListener(R.id.action_post_to_comments)
        toCommentsButton?.setOnClickListener(action)

        return view


    }

}