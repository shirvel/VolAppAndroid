package com.example.app.Modules.Comments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Model.Comment
import com.example.app.Model.CommentListModel
import com.example.app.Model.Post
import com.example.app.Model.PostListModel
import com.example.app.Modules.Comments.Adapter.CommentAdapter
import com.example.app.Modules.Posts.Adapter.AllPostsAdapter
import com.example.app.Modules.Posts.AllPosts
import com.example.app.Modules.Posts.AllPostsDirections
import com.example.app.R

class Comments : Fragment() {
    var allCommentsView: RecyclerView? = null
    var commentsList: MutableList<Comment>? = null
    var addCommentButton: ImageButton? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_comments, container, false)

        commentsList = CommentListModel.instance.comments

        allCommentsView = view.findViewById(R.id.rvAllCommentsFragment)
        // To be more efficient - because it is the same size.
        allCommentsView?.setHasFixedSize(true)

        // Set the layout manager and adapter
        allCommentsView?.layoutManager = LinearLayoutManager(context)

        // Set the adapter
        val adapter = CommentAdapter(commentsList)
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val post = commentsList!!.get(position)
                post.let {
                    Log.i("TAG", "Posts Adapter: Position clicked  $position")
                    val action = AllPostsDirections.actionAllPostsToPost(it.writer)
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
        allCommentsView?.adapter = adapter

        addCommentButton = view.findViewById(R.id.btnAddComment)
//        val action = Navigation.createNavigateOnClickListener(R.id.action_post_to_comments)
//        addCommentButton?.setOnClickListener(action)


        return view
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }


}