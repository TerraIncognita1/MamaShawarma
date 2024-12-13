package org.mrromych.activity.map

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.MapFragment
import org.mrromych.activity.auth.LogInActivity
import org.mrromych.activity.auth.SignUpActivity
import org.mrromych.databinding.PlaceSetterActivityBinding
import kotlin.system.exitProcess

class PlaceSetterActivity: AppCompatActivity() {
    private val binding: PlaceSetterActivityBinding by lazy {
        PlaceSetterActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}