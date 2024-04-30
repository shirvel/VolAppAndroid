package com.example.app.Modules.Comments

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
import com.example.app.model.CommentListModel
import com.example.app.model.Comment
import com.example.app.Modules.Comments.Adapter.CommentsRecyclerAdapter
import com.example.app.R
import com.example.app.databinding.ActivityCommentsRcyclerViewBinding
class CommentsRcyclerViewActivity : AppCompatActivity() {

    var commentsRcyclerView: RecyclerView? = null
    var comments: List<Comment>? = null
    var adapter: CommentsRecyclerAdapter? = null
    private lateinit var binding: ActivityCommentsRcyclerViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_comments_rcycler_view)
        binding = ActivityCommentsRcyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CommentListModel.instance.getAllComments { comments ->
            this.comments = comments
            adapter?.comments = comments
            adapter?.notifyDataSetChanged()
        }

        commentsRcyclerView = binding.rvCommentsREcyclerList
        commentsRcyclerView?.setHasFixedSize(true)
        commentsRcyclerView?.layoutManager = LinearLayoutManager(this)

        adapter = CommentsRecyclerAdapter(comments)
        adapter?.listener = object : OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "CommentsRecyclerAdapter: Position clicked $position")
            }

            override fun onCommentClicked(comment: Comment?) {
                Log.i("TAG", "COMMENT $comment")
            }
        }

        commentsRcyclerView?.adapter = adapter
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onCommentClicked(comment: Comment?)
    }

    override fun onResume() {
        super.onResume()

        CommentListModel.instance.getAllComments { comments ->
            this.comments = comments
            adapter?.comments = comments
            adapter?.notifyDataSetChanged()
        }
    }
}