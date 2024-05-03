package com.example.app.Modules.Posts.Adapter
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Post
import android.widget.TextView
import com.example.app.Modules.Posts.AllPosts
import com.example.app.R

class PostViewHolder(val itemView: View,
                     val listener: AllPosts.OnItemClickListener?,
                     var posts: List<Post>?): RecyclerView.ViewHolder(itemView) {

    var titleTextView: TextView? = null
    var contentTextView: TextView? = null
    var post: Post? = null

    init {
        titleTextView = itemView.findViewById(R.id.tvPostListRowTitle)
        contentTextView = itemView.findViewById(R.id.tvPostListRowContent)

        itemView.setOnClickListener {
            Log.i("TAG", "StudentViewHolder: Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onPostClicked(post)
        }
    }

    fun bind(post: Post?) {
        this.post = post
        titleTextView?.text = post?.title
        contentTextView?.text = post?.content
    }
}
