package org.mrromych.activity.auth

import android.content.Intent
import android.os.Bundle
import android.os.Debug
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import org.mrromych.activity.main.MainActivity
import org.mrromych.activity.main.WelcomeActivity
import org.mrromych.databinding.LoginActivityBinding

class LogInActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference

    private fun login() {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                task -> if (task.isSuccessful) {
            val user = auth.currentUser
            user?.let {
                println("User registered with UID: ${it.uid}")
            }
        } else {
            val exception = task.exception
            when (exception) {
                is FirebaseAuthUserCollisionException -> {
                    println("Error: This email is already registered.")
                }
                is FirebaseAuthWeakPasswordException -> {
                    println("Error: Password is too weak. ${exception.reason}")
                }
                is FirebaseAuthInvalidCredentialsException -> {
                    println("Error: Invalid email format.")
                }
                else -> {
                    println("Error: ${exception?.message}")
                }
            }
        }
        }
    }

    private val binding: LoginActivityBinding by lazy {
        LoginActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        database = Firebase.database.reference

        binding.loginButton.setOnClickListener {
            email = binding.editTextTextEmailAddress.text.toString().trim()
            password = binding.editTextTextPassword.text.toString()
            var error = "Wrong credentials"
            var loginMsg = "Log In successfully"

            login()
            var user = auth.currentUser

            if(user != null) {
                Toast.makeText(this, loginMsg, Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                this.finish()
            }
            else {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        }

        binding.returnButton.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            this.finish()
        }
    }
}