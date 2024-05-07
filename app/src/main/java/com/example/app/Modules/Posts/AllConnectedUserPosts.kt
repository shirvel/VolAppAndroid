package com.example.app.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Modules.Posts.Adapter.PostsRecyclerAdapter
import com.example.app.R
import com.example.app.databinding.FragmentAllPostsBinding
import com.example.app.model.Post
import com.example.app.model.PostListModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class AllConnectedUserPosts : Fragment() {

    var postsRcyclerView: RecyclerView? = null
    var adapter: PostsRecyclerAdapter? = null
    private var _binding: FragmentAllPostsBinding? = null
    var progressBar: ProgressBar? = null

    private val binding get() = _binding!!
    private lateinit var postviewmodel : PostsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_all_posts, container, false)
        _binding = FragmentAllPostsBinding.inflate(inflater, container, false)
        val view = binding.root
        val writer = Firebase.auth.currentUser.toString()
        Log.d("writer", "writer: ${writer}")
        postviewmodel = ViewModelProvider(this)[PostsViewModel::class.java]
        progressBar = binding.progressBar
        progressBar?.visibility = View.VISIBLE
        postviewmodel.posts = PostListModel.instance.getAllConnectedUserPosts(writer)

        postsRcyclerView = binding.rvAllPostsFragment
        postsRcyclerView?.setHasFixedSize(true)
        postsRcyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = PostsRecyclerAdapter(postviewmodel.posts?.value, true)
        adapter?.listener = object : AllPosts.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
                val post = postviewmodel.posts?.value?.get(position)
                post?.let {
                    val action = AllPostsDirections.actionAllPostsToPost(it.writer)
                    Navigation.findNavController(view).navigate(action)
                }
            }

            override fun onPostClicked(post: Post?) {
                Log.i("TAG", "Post $post")
            }
        }

        postsRcyclerView?.adapter = adapter

        postviewmodel.posts?.observe(viewLifecycleOwner) {
            adapter?.posts = it
            adapter?.notifyDataSetChanged()
            progressBar?.visibility = View.GONE

        }
        binding.pullToRefresh.setOnRefreshListener {
            reloadData()
        }

        PostListModel.instance.postsListLoadingState.observe(viewLifecycleOwner) { state ->
            binding.pullToRefresh.isRefreshing = state == PostListModel.LoadingState.LOADING
        }
        // Set OnClickListener for the edit button

        return view
    }

    override fun onResume() {
        super.onResume()

        reloadData()
    }

    private fun reloadData() {
        progressBar?.visibility = View.VISIBLE
        PostListModel.instance.refreshgetAllPosts()
        progressBar?.visibility = View.GONE
    }
    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}