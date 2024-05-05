package com.example.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    var bottomNavigationView: BottomNavigationView? = null

    companion object {
        const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomNavigationView = findViewById(R.id.mainActivityBottomNavigationView)
        // Initialize navigation
        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
        navController = navHostFragment?.navController
        navController?.let { NavigationUI.setupActionBarWithNavController(this, it) }
        navController?.let { NavigationUI.setupWithNavController(bottomNavigationView!!, it) }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                navController?.navigateUp()
                true
            }
            else -> navController?.let { NavigationUI.onNavDestinationSelected(item, it) }
                ?: super.onOptionsItemSelected(item)
        }
    }

    fun hideBottomNavigationView() {
        bottomNavigationView?.setVisibility(View.GONE)
    }

    fun showBottomNavigationView() {
        bottomNavigationView?.setVisibility(View.VISIBLE)
    }
}
