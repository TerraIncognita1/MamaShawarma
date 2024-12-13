package org.mrromych.activity.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.mrromych.databinding.MainActivityBinding
import org.mrromych.R
import org.mrromych.ui.theme.fragments.navigation.CartFragment
import org.mrromych.ui.theme.fragments.navigation.FoodFragment
import org.mrromych.ui.theme.fragments.navigation.ProfileFragment

class MainActivity: AppCompatActivity() {
    private val binding: MainActivityBinding by lazy {
        MainActivityBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val bottomNavigationView = binding.bottomNavigation

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainer.id, FoodFragment())
                .commit()
        }

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            val selectedFragment = when (menuItem.itemId) {
                R.id.nav_food -> FoodFragment()
                R.id.nav_cart -> CartFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }

            if (selectedFragment != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, selectedFragment)
                    .commit()
                true
            } else {
                false
            }
        }
    }
}