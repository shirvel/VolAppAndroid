package com.example.app.Modules.Comments

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
import com.example.app.model.Comment
import com.example.app.model.CommentListModel
import com.example.app.Modules.Comments.Adapter.CommentsRecyclerAdapter
import com.example.app.Modules.Posts.AllPostsDirections
import com.example.app.databinding.FragmentCommentsBinding


class Comments : Fragment() {
    var commentsRcyclerView: RecyclerView? = null
    var adapter: CommentsRecyclerAdapter? = null
    private var _binding: FragmentCommentsBinding? = null
    var progressBar: ProgressBar? = null
    private val binding get() = _binding!!
    private lateinit var commentviewmodel : CommentViewModel
        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // val view = inflater.inflate(R.layout.fragment_comments, container, false)

            _binding = FragmentCommentsBinding.inflate(inflater, container, false)
            val view = binding.root
            commentviewmodel = ViewModelProvider(this)[CommentViewModel::class.java]
            //progressBar = binding.progressBar
            progressBar?.visibility = View.VISIBLE
            commentviewmodel.comments = CommentListModel.instance.getAllCommants()
            commentsRcyclerView = binding.rvAllCommentsFragment
            commentsRcyclerView?.setHasFixedSize(true)
            commentsRcyclerView?.layoutManager = LinearLayoutManager(context)
            adapter = CommentsRecyclerAdapter(commentviewmodel.comments?.value)
            adapter?.listener = object : OnItemClickListener {

                override fun onItemClick(position: Int) {
                    Log.i("TAG", "PostsRecyclerAdapter: Position clicked $position")
                    val comment = commentviewmodel.comments?.value?.get(position)
                    comment?.let {
                        val action = AllPostsDirections.actionAllPostsToPost(it.writer)
                        Navigation.findNavController(view).navigate(action)
                    }
                }

                override fun onCommentClicked(comment: Comment?) {
                    Log.i("TAG", "Comment $comment")
                }
            }

            commentsRcyclerView?.adapter = adapter

            commentviewmodel.comments?.observe(viewLifecycleOwner) {
                adapter?.comments = it
                adapter?.notifyDataSetChanged()
                progressBar?.visibility = View.GONE

            }
            binding.pullToRefresh.setOnRefreshListener {
                reloadData()
            }

            CommentListModel.instance.commentsListLoadingState.observe(viewLifecycleOwner) { state ->
                binding.pullToRefresh.isRefreshing = state == CommentListModel.LoadingState.LOADING
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
        CommentListModel.instance.refreshgetAllComments()
        progressBar?.visibility = View.GONE
    }
    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
        fun onCommentClicked(comment: Comment?)
    }
}