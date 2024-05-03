package com.example.app

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.app.model.UserListModel

class LoginFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

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

            UserListModel.instance.signIn(view, email, password) {
                findNavController().navigate(R.id.action_loginFragment_to_allPostFragment)
            }
        }
    }

    private fun clickRegisterButton() {
        registerButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        // Hide the bottom navigation menu
        if (activity != null && activity is MainActivity) {
            (activity as MainActivity?)!!.hideBottomNavigationView()
        }
    }

    override fun onPause() {
        super.onPause()
        // Restore the bottom navigation menu visibility
        if (activity != null && activity is MainActivity) {
            (activity as MainActivity?)!!.showBottomNavigationView()
        }
    }


}
