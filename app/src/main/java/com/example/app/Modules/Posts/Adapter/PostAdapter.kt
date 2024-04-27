package com.example.app.Modules.Posts.Adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Post
import com.example.app.databinding.PostRowBinding
import com.example.app.Modules.Posts.AllPosts
class AllPostsAdapter(var postsList: MutableList<Post>?): RecyclerView.Adapter<PostViewHolder>() {
    var listener: AllPosts.OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = PostRowBinding.inflate(layoutInflater, parent, false)
        return PostViewHolder(binding, listener)
    }

    override fun getItemCount(): Int = postsList?.size ?: 0

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = postsList?.get(position)
        holder.bind(post)
    }
}
