package com.example.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.app.database.AppDatabase
import com.example.app.model.Post
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private lateinit var db: AppDatabase // Declare AppDatabase variable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize the database instance
        db = AppDatabase.getInstance(this)

        // Insert a sample post into the database (this will happen only once when the MainActivity is created)
        CoroutineScope(Dispatchers.IO).launch {
            val postDao = db.postDao()
            val samplePost = Post(writer = "John Doe", title = "test", content = "Hello, world!", image = "", isLiked = false)
            postDao.insert(samplePost)
        }

        val navHostFragment: NavHostFragment? =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment
        navController = navHostFragment?.navController
        navController?.let { NavigationUI.setupActionBarWithNavController(this, it) }

        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.mainActivityBottomNavigationView)
        navController?.let { NavigationUI.setupWithNavController(bottomNavigationView, it) }

        // Check the database content asynchronously
        CoroutineScope(Dispatchers.Main).launch {
            checkDatabaseContent()
        }
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
//            R.id.miNavbarAddPost -> {
//                val action = AllPostsDirections.actionAllPostsToAddPost() // TODO: Make it a global action.
//                navController?.navigate(action)
//                true
//            }
            // When the id of the item menu is the same as the fragment, it works automatically.
            else -> navController?.let { NavigationUI.onNavDestinationSelected(item, it) }
                ?: super.onOptionsItemSelected(item)
        }
    }

    // Function to check the database content
    private suspend fun checkDatabaseContent() {
        // Retrieve all posts from the database
        val posts = withContext(Dispatchers.IO) {
            db.postDao().getAllPosts()
        }

        // Print the content of each post
        for (post in posts) {
            println("Post ID: ${post.postId}, Writer: ${post.writer}, title: ${post.title}, Content: ${post.content}, Image: ${post.image}, Is Liked: ${post.isLiked}")
        }
    }
}
