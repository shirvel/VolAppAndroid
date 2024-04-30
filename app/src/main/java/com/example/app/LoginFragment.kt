// LoginFragment.kt

package com.example.app

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.app.MainActivity.Companion.TAG
import com.example.app.model.User
import com.example.app.model.UserListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class LoginFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null

    private var loginButton: Button? = null
    private var registerButton: Button? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_login, container, false)

        setUpUI(view)
        return view
    }

    private fun setUpUI(view: View) {

        emailTextField = view.findViewById(R.id.email)
        passwordTextField = view.findViewById(R.id.password)

        loginButton = view.findViewById(R.id.login_button)
        registerButton = view.findViewById(R.id.register_button)


      //  clickLoginButton();
        clickRegisterButton();
    }




//    private fun clickLoginButton() {
//
//        loginButton?.setOnClickListener {
//            val email = emailTextField?.text.toString()
//            val password = passwordTextField?.text.toString()
//
//
//
//
//        }
//    }


    // findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)


    private fun clickRegisterButton() {
        registerButton?.setOnClickListener {
            Navigation.findNavController(it).popBackStack(R.id.action_loginFragment_to_signUpFragment, false)
        }
    }


}

