package com.example.taskat.core.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.taskat.R
import com.example.taskat.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        try {
            setSupportActionBar(binding.toolbar)
            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.navHostContainer) as NavHostFragment
            val navController = navHostFragment.navController
            val appBarConfiguration = AppBarConfiguration(navController.graph, binding.drawerLayout)
            binding.toolbar.setupWithNavController(navController, appBarConfiguration)

            binding.navigationView.setupWithNavController(navController)
        } catch (ex: Exception) {
            Log.d("?????????", "onCreate:${ex.message}")
        }


    }
}