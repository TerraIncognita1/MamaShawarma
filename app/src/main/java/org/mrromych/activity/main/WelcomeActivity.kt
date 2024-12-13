package org.mrromych.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.mrromych.activity.auth.LogInActivity
import org.mrromych.activity.auth.SignUpActivity
import org.mrromych.databinding.WelcomeActivityBinding
import kotlin.system.exitProcess

class WelcomeActivity: AppCompatActivity() {
    private val binding: WelcomeActivityBinding by lazy {
        WelcomeActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        binding.registerButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            this.finish()
        }

        binding.exitButton.setOnClickListener {
            this.finish()
            exitProcess(0)
        }
    }
}