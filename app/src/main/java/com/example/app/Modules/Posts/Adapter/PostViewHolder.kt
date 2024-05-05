package com.example.app.Modules.Posts.Adapter
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Post
import android.widget.TextView
import com.example.app.Modules.Posts.AllPosts
import com.example.app.R
import com.bumptech.glide.Glide
import android.widget.ImageView
import android.net.Uri
class PostViewHolder(val itemView: View,
                     val listener: AllPosts.OnItemClickListener?,
                     var posts: List<Post>?): RecyclerView.ViewHolder(itemView) {

    var titleTextView: TextView? = null
    var contentTextView: TextView? = null
    var addrerssTextView: TextView? = null
    var imageTextView: ImageView? = null
    var post: Post? = null

    init {
        titleTextView = itemView.findViewById(R.id.tvPostListRowTitle)
        contentTextView = itemView.findViewById(R.id.tvPostListRowContent)
        addrerssTextView = itemView.findViewById(R.id.tvPostListRowAddress)
        imageTextView = itemView.findViewById(R.id.ivPostListRowImage)

        itemView.setOnClickListener {
            Log.i("TAG", "Position clicked $adapterPosition")

            listener?.onItemClick(adapterPosition)
            listener?.onPostClicked(post)
        }
    }

    fun bind(post: Post?) {
        this.post = post
        titleTextView?.text = post?.title
        contentTextView?.text = post?.content
        addrerssTextView?.text = post?.address
        //Check if the image URI is not empty
        if (!post?.image.isNullOrEmpty()) {
            // Load the image from URI using Glide or any other image loading library
            Glide.with(itemView)
                .load(Uri.parse(post?.image))
                //.placeholder(R.drawable.viewpic) // Placeholder in case the image loading fails
                //.error(R.drawable.viewpic) // Placeholder for the case of error in loading
                .into(imageTextView!!)
        } else {
            // If image URI is empty, set a default image
            imageTextView?.setImageResource(R.drawable.viewpic)
        }
    }
}
