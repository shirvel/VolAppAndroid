package com.example.app.Modules.Posts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.CheckBox
import android.widget.ListView
import android.widget.TextView
import com.example.app.model.PostListModel
import com.example.app.model.Post
import com.example.app.R
import android.widget.ImageView


class PostsListActivity : AppCompatActivity() {

    var postsListView: ListView? = null
    var posts: List<Post>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_posts_list)

       // PostListModel.instance.getAllPosts { posts ->
        //    this.posts = posts
       // }

        postsListView = findViewById(R.id.lvPosttList)
        postsListView?.adapter = PostsListAdapter(posts)

        postsListView?.setOnItemClickListener { parent, view, position, id ->
            Log.i("TAG", "Row was clicked at: $position")
        }
    }

    class PostsListAdapter(val posts: List<Post>?): BaseAdapter() {

        override fun getCount(): Int = posts?.size ?: 0

        override fun getItem(position: Int): Any {
            TODO("Not yet implemented")
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val post = posts?.get(position)
            var view: View? = null
            if (convertView == null) {
                view = LayoutInflater.from(parent?.context).inflate(R.layout.post_row, parent, false)
            }

            view = view ?: convertView

            val titleTextView: TextView? = view?.findViewById(R.id.tvPostListRowTitle)
            val contentTextView: TextView? = view?.findViewById(R.id.tvPostListRowContent)


            titleTextView?.text = post?.title
            contentTextView?.text = post?.content

            return view!!
        }

        override fun getItemId(position: Int): Long = 0
    }
}