package com.example.applicationquiz

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.applicationquiz.databinding.ActivitySignUpPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.shashank.sony.fancytoastlib.FancyToast

class SignUpPage : AppCompatActivity() {
    private val binding: ActivitySignUpPageBinding by lazy{
        ActivitySignUpPageBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        binding.regLoginBtn.setOnClickListener {
            startActivity(Intent(this,LogInPage::class.java))
            finish()
        }

        binding.regBtn.setOnClickListener{
            val email = binding.regEmail.text.toString()
            val userName = binding.regUserName.text.toString()
            val password = binding.regPassword.text.toString()

            // check if any field is blank
            if(email.isEmpty() || userName.isEmpty() || password.isEmpty()){
                Toast.makeText(this, "Fill all details", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this){
                        task ->
                    if(task.isSuccessful){
                        FancyToast.makeText(this, "Registration Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show()
//                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,QuizActivity::class.java))
                        finish()
                    }
                    else{
                        FancyToast.makeText(this, "Registration Unsuccessful", FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show()
//                        Toast.makeText(this, "Registration Unsuccessful", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}