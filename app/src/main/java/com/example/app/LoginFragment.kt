package com.example.app

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import com.example.app.model.UserListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        auth = FirebaseAuth.getInstance()

        setUpUI(view)
        return view
    }

    private fun setUpUI(view: View) {
        emailTextField = view.findViewById(R.id.editTextEmail)
        passwordTextField = view.findViewById(R.id.editTextPassword)
        loginButton = view.findViewById(R.id.btnLogin)
        registerButton = view.findViewById(R.id.btnRegister)

        clickLoginButton(view)
        clickRegisterButton()
    }

    private fun clickLoginButton(view: View) {
        loginButton?.setOnClickListener {
            val email = emailTextField?.text.toString()
            val password = passwordTextField?.text.toString()

            loginUser(email, password)

//            if (email.isNotEmpty() && password.isNotEmpty()) {
////                loginUser(email, password)
//
////                UserListModel.instance.signIn(view, email, password) {
////                    findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)
////                }
//            }
//
//            else {
//                // TODO: POPUP: the fields are empty...
//            }


        }
    }

    private fun loginUser(email: String, password: String) {

        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)
            }.addOnFailureListener {
                findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
            }
//        auth.signInWithEmailAndPassword(email, password)
//            .addOnCompleteListener(requireActivity()) { task ->
//                if (task.isSuccessful) {
//                    findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)
//                    // Login successful
//                    // Navigate to the main activity or perform other actions
//                } else {
//
//                    findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
//                    // Login failed
//                    // Handle failure, e.g., display error message
//                }
//            }
    }

    private fun clickRegisterButton() {
        registerButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

}
