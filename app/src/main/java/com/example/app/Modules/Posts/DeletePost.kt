package com.example.app.Modules.Posts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.app.R

class DeletePost : Fragment() {
    private var confirmButton: Button? = null
    private var cancelButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_delete_post, container, false)
        setupUI(view)
        return view
    }

    private fun setupUI(view: View) {
        confirmButton = view.findViewById(R.id.btnDeletePostConfirm)
        cancelButton = view.findViewById(R.id.btnDeletePostCancel)

        confirmButton?.setOnClickListener {
            // Perform post deletion
            // Call  Model method to delete the post here

        }

        cancelButton?.setOnClickListener {
            // Optionally, navigate back to previous fragment
            // requireActivity().supportFragmentManager.popBackStack()
        }
    }
}
