package org.mrromych.activity.auth

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database
import org.mrromych.databinding.RegisterActivityBinding
import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import org.mrromych.activity.main.MainActivity
import org.mrromych.activity.main.WelcomeActivity
import org.mrromych.activity.map.PlaceSetterActivity
import org.mrromych.validation.Validation

class SignUpActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var database: DatabaseReference

    private fun createAccount() {
        val successNotification = "User registered successfully!"

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            task -> if (task.isSuccessful) {
                val user = auth.currentUser
                user?.let {
                    println("User registered with UID: ${it.uid}")
                }
                Toast.makeText(this, successNotification, Toast.LENGTH_SHORT).show()
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

    private val binding: RegisterActivityBinding by lazy {
        RegisterActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = Firebase.auth
        database = Firebase.database.reference


        binding.registerButton.setOnClickListener {
            username = binding.editTextUsername.text.toString().trim()
            email = binding.editTextTextEmailAddress.text.toString().trim()
            password = binding.editTextTextPassword.text.toString()
            var error = "Wrong credentials"

            if(Validation.validateRegistration(username, email, password)) {
                createAccount()
                val user = auth.currentUser

                if(user != null){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    this.finish()
                }
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