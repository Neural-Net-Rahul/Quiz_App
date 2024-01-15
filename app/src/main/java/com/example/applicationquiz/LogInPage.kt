package com.example.applicationquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.applicationquiz.databinding.ActivityLogInPageBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.shashank.sony.fancytoastlib.FancyToast

class LogInPage : AppCompatActivity() {
    private val binding: ActivityLogInPageBinding by lazy{
        ActivityLogInPageBinding.inflate(layoutInflater)
    }

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth = FirebaseAuth.getInstance()

        binding.creatingNewAccount.setOnClickListener{
            startActivity(Intent(this,SignUpPage::class.java))
            finish()
        }
        binding.loginBtn.setOnClickListener {
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()
            if(email.isEmpty() || password.isEmpty()){
                FancyToast.makeText(this, "Fill all details", FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show()
//                Toast.makeText(this,"Fill all details", Toast.LENGTH_SHORT).show()
            }
            else{
                auth.signInWithEmailAndPassword(email,password).addOnCompleteListener{
                        task ->
                    if(task.isSuccessful){
                        FancyToast.makeText(this, "LogIn Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show()
//                        Toast.makeText(this, "LogIn Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this,QuizActivity::class.java))
                        finish()
                    }
                    else{
                        FancyToast.makeText(this, "LogIn Failed  ${task.exception?.message}", FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show()
//                        Toast.makeText(this, "LogIn Failed ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestIdToken("997362186711-g5mqs2n6r161omeqr58p96jbtj1kuin2.apps.googleusercontent.com").requestEmail().build()
        googleSignInClient = GoogleSignIn.getClient(this,gso)
        binding.google.setOnClickListener {
            val signInClient = googleSignInClient.signInIntent
            launcher.launch(signInClient)
        }
    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if(result.resultCode== Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account : GoogleSignInAccount? = task.result
                val credential = GoogleAuthProvider.getCredential(account?.idToken,null)
                auth.signInWithCredential(credential).addOnCompleteListener {
                    if(it.isSuccessful){
                        FancyToast.makeText(this, "LogIn Successful", FancyToast.LENGTH_LONG, FancyToast.SUCCESS,true).show()
                        startActivity(Intent(this,QuizActivity::class.java))
                        finish()
                    }
                    else{
                        FancyToast.makeText(this, "Failed LogIn", FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show()
                    }
                }
            }
        }
        else{
            FancyToast.makeText(this, "Failed LogIn", FancyToast.LENGTH_LONG, FancyToast.WARNING,true).show()
        }
    }
}