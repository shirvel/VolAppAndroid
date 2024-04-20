package com.example.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import android.view.View

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.visibility = View.VISIBLE

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_view_posts -> {
                    // Start activity or load fragment for viewing posts
                    startActivity(Intent(this, PostsList::class.java))
                }
                R.id.nav_create_post -> {
                    // Start activity or load fragment for creating a post
                }
                R.id.nav_your_details -> {
                    // Start activity or load fragment for showing user details
                }
                R.id.nav_logout -> {
                    // Handle logout, clear session, etc.
                    finish()
                }
            }
            drawerLayout.openDrawer(GravityCompat.START)
            true
        }
    }
}
