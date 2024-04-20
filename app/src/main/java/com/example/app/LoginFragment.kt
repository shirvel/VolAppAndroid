// LoginFragment.kt

package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container, false)

        val usernameEditText: EditText = view.findViewById(R.id.username)
        val passwordEditText: EditText = view.findViewById(R.id.password)
        val loginButton: Button = view.findViewById(R.id.login_button)
        val registerButton: Button = view.findViewById(R.id.register_button)

        loginButton.setOnClickListener {
            // Handle login logic here
            // For example, navigate to the main screen if login is successful
            findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)
        }

        registerButton.setOnClickListener {
            // Handle registration navigation here
            // For example, navigate to the registration screen
            // findNavController().navigate(R.id.action_loginFragment_to_registrationFragment) to do!!!
        }

        return view
    }
}

