package com.example.applicationquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.core.os.postDelayed
import com.example.applicationquiz.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        supportActionBar?.hide() // to hide title bar

        // After 3sec, it will be redirected to desired Page
        Handler(Looper.getMainLooper()).postDelayed(2000){

            val currentUser : FirebaseUser? = auth.currentUser
            if(currentUser!=null){
                // if user is already logged in then send him to task page
                startActivity(Intent(this,QuizActivity::class.java))
                finish()
            }
            else{
                // if user is not logged in, send him to LogIn Page
                val intent = Intent(this,LogInPage::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}