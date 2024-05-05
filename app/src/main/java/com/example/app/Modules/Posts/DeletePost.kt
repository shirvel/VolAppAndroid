package com.example.app.Modules.Posts
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.app.databinding.FragmentDeletePostBinding
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import com.example.app.model.PostListModel
import com.example.app.model.Post
import com.example.app.Modules.Posts.AllPosts
import com.example.app.R
import androidx.navigation.NavController


class DeletePost : Fragment() {
    private lateinit var binding: FragmentDeletePostBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeletePostBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnDeletePostConfirm.setOnClickListener {
            arguments?.let { args ->
                val postId = PostArgs.fromBundle(args).postWriter
                PostListModel.instance.deletePost(postId) {
                    val navController = Navigation.findNavController(view)
                    navController.navigate(R.id.allPost)
                }
            }

        }
        binding.btnDeletePostCancel.setOnClickListener{
            val navController = Navigation.findNavController(view)
            navController.navigate(R.id.allPost)
        }

        return view
    }
}