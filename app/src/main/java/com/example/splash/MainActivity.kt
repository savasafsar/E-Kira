package com.example.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.example.splash.api.RestApiService
import com.example.splash.api.SessionManager
import com.example.splash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Main)
        super.onCreate(savedInstanceState)
       binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navController = Navigation.findNavController(this,R.id.navHost)
        setupWithNavController(binding.bottomNavigationView,navController)
    }


    fun logout(view : View) {
        var sm = SessionManager(this@MainActivity)
        val apiService = RestApiService(this@MainActivity)
        apiService.logoutAuth() {
            if (it?.isSuccess == true && it?.result == "OK") {
                sm.saveAuthToken("")
                val intent = Intent(this@MainActivity, RegisterActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                sm.saveAuthToken("")
            }
        }
    }


}