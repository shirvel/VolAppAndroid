package com.example.app.Modules.Comments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.app.model.Comment
import com.example.app.model.CommentListModel
import com.example.app.Modules.Comments.Adapter.CommentsRecyclerAdapter
import com.example.app.Modules.Posts.AllPostsDirections
import com.example.app.R
import com.example.app.databinding.FragmentCommentsBinding


class Comments : Fragment() {
    var allCommentsView: RecyclerView? = null
    var commentsList: List<Comment>? = null
    var addCommentButton: ImageButton? = null
    var adapter: CommentsRecyclerAdapter? = null
    private var _binding: FragmentCommentsBinding? = null
    private val binding get() = _binding!!
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // val view = inflater.inflate(R.layout.fragment_comments, container, false)

            _binding = FragmentCommentsBinding.inflate(inflater, container, false)
            val view = binding.root
        CommentListModel.instance.getAllComments { commentsList ->
            this.commentsList = commentsList
            adapter?.comments = commentsList
            adapter?.notifyDataSetChanged()
        }

        allCommentsView = binding.rvAllCommentsFragment
        // To be more efficient - because it is the same size.
        allCommentsView?.setHasFixedSize(true)

        // Set the layout manager and adapter
        allCommentsView?.layoutManager = LinearLayoutManager(context)

        // Set the adapter
        val adapter = CommentsRecyclerAdapter(commentsList)
        adapter?.listener = object : CommentsRcyclerViewActivity.OnItemClickListener {

            override fun onItemClick(position: Int) {
                Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
                val comment = commentsList?.get(position)
                comment?.let {
                    val action = AllPostsDirections.actionAllPostsToPost(it.writer)
                    Navigation.findNavController(view).navigate(action)

                }
            }
                override fun onCommentClicked(comment: Comment?) {
                    Log.i("TAG", "Post $comment")
            }
        }
        allCommentsView?.adapter = adapter

        addCommentButton = view.findViewById(R.id.btnAddComment)
//        val action = Navigation.createNavigateOnClickListener(R.id.action_post_to_comments)
//        addCommentButton?.setOnClickListener(action)


        return view
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}