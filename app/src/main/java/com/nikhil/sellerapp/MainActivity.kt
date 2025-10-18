package com.nikhil.sellerapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.nikhil.sellerapp.Signup.SignUpActivity
import com.nikhil.sellerapp.databinding.ActivityMainBinding
import com.nikhil.sellerapp.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {

    // Binding for the layout
    private lateinit var binding: ActivityMainBinding

    // Lazily get FirebaseAuth AFTER FirebaseApp has been initialized
    // Using lazy ensures this runs only when first accessed (i.e. after onCreate if we access it later)
    private val auth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }

    // Lazily get Firestore instance as well
    private val db: FirebaseFirestore by lazy { com.google.firebase.firestore.FirebaseFirestore.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FirebaseApp as early as possible.
        // Ideally you do this once in a custom Application class (recommended).
        FirebaseApp.initializeApp(this)

        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Apply window insets (status bar / nav bar padding).
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // initial visibility for title items
        binding.tvtitlee.visibility = android.view.View.INVISIBLE
        binding.tvtitlee2.visibility = android.view.View.INVISIBLE

        // Lottie animation setup
        binding.lottiee.setAnimation(R.raw.money)
        binding.lottiee.repeatCount = 0
        binding.lottiee.speed = 1.0f

        binding.lottiee.addLottieOnCompositionLoadedListener { composition ->
            binding.lottiee.playAnimation()

            binding.tvtitlee.visibility = android.view.View.VISIBLE
            binding.tvtitlee2.visibility = android.view.View.VISIBLE
            binding.tvtitlee.alpha = 0f
            binding.tvtitlee2.alpha = 0f
            binding.tvtitlee.animate().alpha(1f).setDuration(300).start()
            binding.tvtitlee2.animate().alpha(1f).setDuration(500).start()
        }
    }

    override fun onStart() {
        super.onStart()

        // Get current user (this will be persisted by Firebase if everything is set up correctly)
        val user = auth.currentUser

        // If user is null -> not signed in -> go to SignUpActivity
        if (user == null) {
            navigateTo(SignUpActivity::class.java)
            return
        }

        // If user exists, check profile completion in Firestore and route accordingly
        lifecycleScope.launch {
            val uid = user.uid
            val profComplete = check(uid) // returns true/false (never null)
            val destClass = if (profComplete) HomeActivity::class.java else SignUpActivity::class.java

            // Optional splash delay for animation; adjust or remove as desired
            delay(3500L)
            navigateTo(destClass)
        }
    }

    // Centralized navigation helper that clears the activity stack
    private fun navigateTo(destClass: Class<*>) {
        val intent = Intent(this, destClass)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    // Check function returns boolean (false on any problem) — easier to reason about
    private suspend fun check(uid: String): Boolean {
        return try {
            val document = db.collection("Users").document(uid).get().await()
            // safely return boolean; if field is missing default to false
            document.getBoolean("profilecomplete") ?: false
        } catch (e: Exception) {

            false
        }
    }
}
