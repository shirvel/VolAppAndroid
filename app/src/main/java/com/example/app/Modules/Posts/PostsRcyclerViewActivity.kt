package com.example.app.Modules.Posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.PostListModel
import com.example.app.model.Post
import com.example.app.Modules.Posts.Adapter.PostsRecyclerAdapter
import com.example.app.R
import com.example.app.databinding.ActivityPostsRcyclerViewBinding
class PostsRcyclerViewActivity : AppCompatActivity() {

    var postsRcyclerView: RecyclerView? = null
    var posts: List<Post>? = null
    var adapter: PostsRecyclerAdapter? = null
    private lateinit var binding: ActivityPostsRcyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_posts_rcycler_view)
        binding = ActivityPostsRcyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // PostListModel.instance.getAllPosts { posts ->
       //     this.posts = posts
        //    adapter?.posts = posts
        //    adapter?.notifyDataSetChanged()
       // }

        postsRcyclerView = binding.rvPostsREcyclerList
        postsRcyclerView?.setHasFixedSize(true)
        postsRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = PostsRecyclerAdapter(posts)
        adapter?.listener = object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
            }

            override fun onPostClicked(post: Post?) {
                Log.i("TAG", "POST $post")
            }
        }

        postsRcyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onPostClicked(post: Post?)
    }

    override fun onResume() {
        super.onResume()

       // PostListModel.instance.getAllPosts { posts ->
        //    this.posts = posts
       //     adapter?.posts = posts
        //    adapter?.notifyDataSetChanged()
       // }
    }
}