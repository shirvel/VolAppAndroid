package com.example.app.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Model.Post
import com.example.app.Model.PostListModel
import com.example.app.Modules.Posts.Adapter.AllPostsAdapter
import com.example.app.R


class AllPosts : Fragment() {
    var allPostsView: RecyclerView? = null
    var postsList: MutableList<Post>? = null
    var addPostButton: ImageButton? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_all_posts, container, false)

        postsList = PostListModel.instance.posts

        allPostsView = view.findViewById(R.id.rvAllPostsFragment)
        // To be more efficient - because it is the same size.
        allPostsView?.setHasFixedSize(true)

        // Set the layout manager and adapter
        allPostsView?.layoutManager = LinearLayoutManager(context)

        // Set the adapter
        val adapter = AllPostsAdapter(postsList)
        adapter.listener = object : OnItemClickListener {
            override fun onItemClick(position: Int) {
                val post = postsList!!.get(position)
                post.let {
                    Log.i("TAG", "Posts Adapter: Position clicked  $position")
                    val action = AllPostsDirections.actionAllPostsToPost(it.writer)
                    Navigation.findNavController(view).navigate(action)
                }
            }
        }
        allPostsView?.adapter = adapter

        addPostButton = view.findViewById(R.id.btnAddPost)
        val action = Navigation.createNavigateOnClickListener(R.id.action_allPosts_to_addPost)
        addPostButton?.setOnClickListener(action)


        return view
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }

}