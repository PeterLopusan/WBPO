package com.example.wbpoapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import com.example.wbpoapp.R
import com.example.wbpoapp.databinding.ActivityMainBinding
import com.example.wbpoapp.sharedPreferences.SharedPreferencesManager

class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        instance = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.navController

        if (!SharedPreferencesManager().getFirstRegistration()) {
            val navOptions: NavOptions = NavOptions.Builder()
                .setPopUpTo(R.id.userListFragment, true)
                .build()
            try {
                navController.navigate(R.id.action_registrationFragment_to_userListFragment, null, navOptions)
            } catch (_: Exception) {
            }
        }
    }

    fun findNavController(): NavController? {
        val navHostFragment = this.supportFragmentManager.findFragmentById(R.id.nav_host) as? NavHostFragment
        return navHostFragment?.navController
    }


    companion object {
        lateinit var instance: MainActivity
            private set
    }
}