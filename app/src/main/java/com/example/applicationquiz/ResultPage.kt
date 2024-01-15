package com.example.applicationquiz

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.applicationquiz.databinding.ActivityResultPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class ResultPage : AppCompatActivity() {
    private lateinit var binding:ActivityResultPageBinding

    private lateinit var auth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("SCORE",0).toString()
        lateinit var performance:String
        if(score=="0"){
            performance = "Worst"
        }
        else if(score=="1"){
            performance = "Bad"
        }
        else if(score=="2"){
            performance = "Decent"
        }
        else if(score=="3"){
            performance = "Good"
        }
        else if(score=="4"){
            performance = "Very Good"
        }
        else{
            performance = "Excellent"
        }

        binding.finalScore.text = "Final Score : $score"
        binding.performance.text = "Performance : $performance"

        binding.logOutBtn.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("1090035990519-72fhqrdosna2kc6vj57sfbenbdruk1rr.apps.googleusercontent.com").requestEmail().build()
            GoogleSignIn.getClient(this,gso).signOut()

            startActivity(Intent(this,LogInPage::class.java))
            finish()
        }
    }
}