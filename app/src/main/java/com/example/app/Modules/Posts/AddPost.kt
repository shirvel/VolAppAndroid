package com.example.app.Modules.Posts
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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




class AddPost : Fragment() {

    private var nameTextField: EditText? = null
    private var contentTextField: EditText? = null
    private var messageTextView: TextView? = null
    private var saveButton: Button? = null
    private var cancelButton: Button? = null
    private var locationText: EditText? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        nameTextField = view.findViewById(R.id.etAddPostTitle)
        contentTextField = view.findViewById(R.id.etAddPostContent)
        locationText = view.findViewById(R.id.etAddPostLocation)
        messageTextView = view.findViewById(R.id.textViewContent)
        saveButton = view.findViewById(R.id.btnAddPostSave)
        cancelButton = view.findViewById(R.id.btnAddPostCancel)
        messageTextView?.text = ""

        cancelButton?.setOnClickListener {
            val navController = Navigation.findNavController(view)
            // Pop back to LoginFragment to clear the back stack
            navController.popBackStack(R.id.LoginFragment, false)
            // Navigate to AllPosts fragment
            navController.navigate(R.id.allPost)
        }

        saveButton?.setOnClickListener {
            Log.i("TAG", "the post to add here")
            val name = nameTextField?.text.toString()
            val content = contentTextField?.text.toString()
            val address = locationText?.text.toString()
            Log.i("TAG", "the post to add ${address}")
            val post = Post(name, "", content, "", false, address)

            PostListModel.instance.addPost(post) {
                val navController = Navigation.findNavController(view)
                // Pop back to LoginFragment to clear the back stack
                //navController.popBackStack(R.id.LoginFragment, false)
                // Navigate to AllPosts fragment
                navController.navigate(R.id.allPost)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }
}