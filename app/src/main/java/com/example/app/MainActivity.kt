package com.example.app

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.app.database.AppDatabase
import com.example.app.model.Post
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var navController: NavController? = null
    private lateinit var db: AppDatabase // Declare AppDatabase variable

    companion object {
        const val TAG = "MainActivity"
    }

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

// Access Firestore database
        val firestoreDB = FirebaseFirestore.getInstance()

        // Define a data model class (e.g., Post)
        data class FirestorePost(
            val title: String = "",
            val content: String = ""
        )

        // Add a new document to the "posts" collection
        val firestorePost = FirestorePost("Example Post Title", "This is the content of the post.")

        // Add the post to the "posts" collection
        firestoreDB.collection("posts")
            .add(firestorePost)
            .addOnSuccessListener { documentReference ->
                // Document added successfully
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                // Error adding document
                Log.w(TAG, "Error adding document", e)
            }

        // Initialize navigation
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
