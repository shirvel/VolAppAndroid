package com.example.app.Modules.Posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app.R

class LikePost : Fragment() {
    private var likeButton: Button? = null
    private var dislikeButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_like_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        likeButton = view.findViewById(R.id.btnLikePost)
        dislikeButton = view.findViewById(R.id.btnDislikePost)

        likeButton?.setOnClickListener {
            // Perform like operation
            // Call Model method to like the post here
        }

        dislikeButton?.setOnClickListener {
            // Perform dislike operation
            // Call Model method to dislike the post here
        }
    }
}
