package com.nikhil.sellerapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.google.firebase.Firebase
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.nikhil.sellerapp.Signup.SignUpActivity
import com.nikhil.sellerapp.databinding.ActivityMainBinding
import com.nikhil.sellerapp.home.HomeActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val auth:FirebaseAuth=FirebaseAuth.getInstance()
    private val uid=auth.currentUser?.uid
    val db= Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.tvtitlee.visibility = View.INVISIBLE
        binding.tvtitlee2.visibility = View.INVISIBLE


        binding.lottiee.setAnimation(R.raw.money)
        binding.lottiee.repeatCount = 0
        binding.lottiee.speed = 1.0f


        binding.lottiee.addLottieOnCompositionLoadedListener { composition ->
            binding.lottiee.playAnimation()


            binding.tvtitlee.visibility = View.VISIBLE
            binding.tvtitlee2.visibility = View.VISIBLE
            binding.tvtitlee.alpha = 0f
            binding.tvtitlee2.alpha = 0f
            binding.tvtitlee.animate().alpha(1f).setDuration(300).start()
            binding.tvtitlee2.animate().alpha(1f).setDuration(500).start()

//            val duration = (composition?.duration ?: 1000L).toFloat()/(binding.lottiee.speed)
//
//            Handler(Looper.getMainLooper()).postDelayed({
//                startActivity(Intent(this, SignUpActivity::class.java))
//                finish()
//            }, duration.toLong())
        }

    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser

        // If user is null, go directly to SignUpActivity
        if (user == null) {
            navigateTo(SignUpActivity::class.java)
            return
        }

       else {
            val uid = user.uid
            lifecycleScope.launch {

                val status = check(uid)
                val destClass =
                    if (status == true) HomeActivity::class.java else SignUpActivity::class.java

                // Optional splash delay
                delay(3500) // 3500ms = 3.5s

                navigateTo(destClass)
            }
        }
    }



private fun navigateTo(destClass: Class<*>) {
    val intent = Intent(this, destClass)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    startActivity(intent)
    finish()
}

    private suspend fun check(uid:String): Boolean? {
        return try{
            val document=db.collection("Users").document(uid).get().await()
            document.getBoolean("profcomp")
        }catch(e:Exception){
            null
        }
    }

    }
