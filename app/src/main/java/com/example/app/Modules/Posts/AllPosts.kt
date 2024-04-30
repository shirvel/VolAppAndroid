package com.example.app.Modules.Posts

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Post
import com.example.app.model.PostListModel
import com.example.app.Modules.Posts.Adapter.PostsRecyclerAdapter
import com.example.app.R
import com.example.app.databinding.FragmentAllPostsBinding




class AllPosts : Fragment() {

    var postsRcyclerView: RecyclerView? = null
    var posts: List<Post>? = null
    var adapter: PostsRecyclerAdapter? = null
    private var _binding: FragmentAllPostsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //val view = inflater.inflate(R.layout.fragment_all_posts, container, false)
        _binding = FragmentAllPostsBinding.inflate(inflater, container, false)
        val view = binding.root
        PostListModel.instance.getAllPosts { posts ->
            this.posts = posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()
        }

        postsRcyclerView = binding.rvAllPostsFragment
        postsRcyclerView?.setHasFixedSize(true)
        postsRcyclerView?.layoutManager = LinearLayoutManager(context)
        adapter = PostsRecyclerAdapter(posts)
        adapter?.listener = object : PostsRcyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
                val post = posts?.get(position)
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
        return view
    }

    override fun onResume() {
        super.onResume()

        PostListModel.instance.getAllPosts { posts ->
            this.posts = posts
            adapter?.posts = posts
            adapter?.notifyDataSetChanged()
        }
    }
    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}