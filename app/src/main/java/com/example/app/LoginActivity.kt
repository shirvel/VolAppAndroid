package com.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.content.Intent


class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Use activity_main.xml as the layout


        val usernameEditText: EditText = findViewById(R.id.username)
        val passwordEditText: EditText = findViewById(R.id.password)
        val loginButton: Button = findViewById(R.id.login_button)
        val registerButton: Button = findViewById(R.id.register_button)

        // Set up onClickListeners
        loginButton.setOnClickListener {
            // Handle login logic here
            //al drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
            //drawerLayout.openDrawer(GravityCompat.START)
             val intent = Intent(this, PostsList::class.java)
             startActivity(intent)
        }

        registerButton.setOnClickListener {
            // Handle registration navigation here
        }
    }
}
