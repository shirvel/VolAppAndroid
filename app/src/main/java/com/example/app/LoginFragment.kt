package com.example.app

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.app.Modules.weather.Weather
import com.example.app.model.UserListModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginFragment : Fragment() {

    private var emailTextField: EditText? = null
    private var passwordTextField: EditText? = null
    private var textViewHeader: TextView? = null
    private var loginButton: Button? = null
    private var registerButton: Button? = null
    private var logoImage: ImageView? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var weather: Weather
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Indicate that this fragment has its own options menu
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        setUpUI(view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // Get weather data
        weather = Weather(requireContext(), requireActivity())

        textViewHeader?.let { headerTextView ->
            weather.getCurrentLocation { location ->
                weather.determineDayOrNight() { dayOrNight ->
                    requireActivity().runOnUiThread {
                        textViewHeader?.text = String.format("Good %s", dayOrNight)
                        if (dayOrNight == "Morning")
                            logoImage?.setImageResource(R.drawable.sun_logo)
                        else
                            logoImage?.setImageResource(R.drawable.moon_logo)
                        fadeInTextView(textViewHeader!!)
                        fadeInTextView(logoImage!!)
                    }
                }
            }
        }
    }

    private fun setUpUI(view: View) {
        emailTextField = view.findViewById(R.id.editTextEmail)
        passwordTextField = view.findViewById(R.id.editTextPassword)
        textViewHeader = view.findViewById(R.id.textViewHeader)
        loginButton = view.findViewById(R.id.btnLogin)
        registerButton = view.findViewById(R.id.btnRegister)
        logoImage = view.findViewById(R.id.imageViewLogo)

        clickLoginButton(view)
        clickRegisterButton()
    }

    private fun clickLoginButton(view: View) {
        loginButton?.setOnClickListener {
            val email = emailTextField?.text.toString()
            val password = passwordTextField?.text.toString()

            if (validateCreds(email, password))
                signIn(email, password)
        }
    }

    private fun validateCreds(email: String, password: String): Boolean {
        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(
                requireContext(),
                "All fields must be filled",
                Toast.LENGTH_LONG,
            ).show()
            return false
        } else {
            return true
        }
    }

    private fun clickRegisterButton() {
        registerButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_signUpFragment)
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = auth.currentUser
                    onSuccess(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        requireContext(),
                        task.exception?.message ?: "Authentication failed.",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
    }

    fun onSuccess(user: FirebaseUser?) {
        // TODO maybe pass user as argument
        findNavController().navigate(R.id.allPost)
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

    private fun fadeInTextView(view: View) {
        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 2000
        fadeIn.fillAfter = true
        view.startAnimation(fadeIn)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        super.onCreateOptionsMenu(menu, inflater)
    }

    companion object {
        private const val TAG = "Login"
    }
}
