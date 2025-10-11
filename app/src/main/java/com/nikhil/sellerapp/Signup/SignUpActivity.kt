package com.nikhil.sellerapp.Signup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.nikhil.sellerapp.Login.LoginActivity
import com.nikhil.sellerapp.R
import com.nikhil.sellerapp.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var onboardingAdapter: OnboardingAdapter
    private var dots: Array<View?> = arrayOfNulls(0)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setupViewPager()
        setupDotsIndicator()
        setupButtonClick()
        binding.firstsignin.setOnClickListener {
            startActivity(Intent(this,LoginActivity::class.java))
        }
    }
    private fun setupViewPager() {
        onboardingAdapter = OnboardingAdapter()
        binding.onboardingViewPager.adapter = onboardingAdapter


        binding.onboardingViewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                updateDots(position)
                updateButtonText(position)
            }
        })
    }

    private fun setupDotsIndicator() {
        val dotsCount = onboardingAdapter.itemCount
        dots = arrayOfNulls(dotsCount)

        // Clear existing dots
        binding.dotsIndicator.removeAllViews()

        // Create dots
        for (i in 0 until dotsCount) {
            dots[i] = View(this)

            // Convert dp to pixels
            val size = (8 * resources.displayMetrics.density).toInt()
            val margin = (6 * resources.displayMetrics.density).toInt()

            val params = android.widget.LinearLayout.LayoutParams(size, size)
            params.setMargins(margin, 0, margin, 0)

            dots[i]?.layoutParams = params
            dots[i]?.background = ContextCompat.getDrawable(
                this,
                if (i == 0) R.drawable.dot_active else R.drawable.dot_inactive
            )

            binding.dotsIndicator.addView(dots[i])
        }
    }

    private fun updateDots(currentPosition: Int) {
        for (i in dots.indices) {
            dots[i]?.background = ContextCompat.getDrawable(
                this,
                if (i == currentPosition) R.drawable.dot_active else R.drawable.dot_inactive
            )
        }
    }

    private fun updateButtonText(position: Int) {
        val lastPage = onboardingAdapter.itemCount - 1
        binding.btnContinue.text = if (position == lastPage) {
            "Get Started"
        } else {
            "Next"
        }
    }

    private fun setupButtonClick() {
        binding.btnContinue.setOnClickListener {
            val currentPosition = binding.onboardingViewPager.currentItem
            val lastPage = onboardingAdapter.itemCount - 1

            if (currentPosition < lastPage) {
                // Go to next page
                binding.onboardingViewPager.currentItem = currentPosition + 1
            } else {
                // Last page - navigate to login/signup
                handleGetStarted()
            }
        }
    }

    private fun handleGetStarted() {
        startActivity(Intent(this,SignUpActivity2::class.java))

    }
}