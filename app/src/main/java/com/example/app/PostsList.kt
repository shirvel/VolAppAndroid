package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.Model.Post
import com.example.app.Model.PostListModel

class PostsList : AppCompatActivity() {
    var allPostsView: RecyclerView? = null
    var postsList: MutableList<Post>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_posts_list)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        postsList = PostListModel.instance.posts

        allPostsView = findViewById(R.id.AllPostsList)
        // To be more efficient - because it is the same size.
        allPostsView?.setHasFixedSize(true)

        // Set the layout manager and adapter
        allPostsView?.layoutManager = LinearLayoutManager(this)

        // Set the adapter
        val adapter = AllPostsAdapter()
        adapter.listener = object : OnItemClickListener{
            override fun onItemClick(position: Int) {
                Log.i("TAG", "Posts Adapter: Position clicked  $position")
            }

        }
        allPostsView?.adapter = adapter

        // On click for item

    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    inner class PostViewHolder(val itemView: View, val listener: OnItemClickListener?): RecyclerView.ViewHolder(itemView){
        var writerTextView: TextView? = null
        var contentTextView: TextView? = null
        var likeCheckBox: CheckBox? = null
        var post: Post? = null

        init {
            writerTextView = itemView.findViewById(R.id.tvPostListRowWriter)
            contentTextView = itemView.findViewById(R.id.tvPostListRowContent)
            likeCheckBox = itemView.findViewById(R.id.btnPostListRowLike)

            likeCheckBox?.setOnClickListener{
                this.post?.isLiked = likeCheckBox?.isChecked ?: false
            }
            itemView.setOnClickListener {
                Log.i("TAG", "View Holder: Posisiotn clicked $adapterPosition")
                listener?.onItemClick(adapterPosition)
            }
        }

        fun bind(post: Post?){
            this.post = post
            writerTextView?.text = post?.writer
            contentTextView?.text = post?.content
            likeCheckBox?.apply {
                isChecked = post?.isLiked ?: false
            }
        }
    }

    inner class AllPostsAdapter: RecyclerView.Adapter<PostViewHolder>() {
        var listener: OnItemClickListener? = null
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
            val itemView = LayoutInflater.from(parent.context).inflate(R.layout.post_row, parent, false)
            return PostViewHolder(itemView, listener)
        }

        override fun getItemCount(): Int = postsList?.size ?: 0

        override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
            // Here we put all the data itself.
            val post = postsList?.get(position)
            holder.bind(post)
        }

    }
}