package com.example.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.splash.api.RestApiService
import com.example.splash.api.SessionManager
import com.example.splash.api.models.LoginPost
import com.example.splash.api.models.RegisterPost
import com.example.splash.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Main)
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    fun signInClicked(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this@RegisterActivity, "Enter email and password", Toast.LENGTH_LONG)
                .show()
        }

        val apiService = RestApiService(this@RegisterActivity)
        val data = LoginPost(  email = email.toString(),
            password = password.toString() )

        apiService.loginPost(data) {
            println(it)
            if (it?.isSuccess == true) {
                println(it?.result)
                if(it?.result != null) {
                    val sm = SessionManager(this@RegisterActivity)
                    sm.saveAuthToken(it?.result.accessToken)
                    var str: String = "Hoş Geldin, %s %s".format(
                        it.result?.user?.firstName,
                        it.result?.user?.lastName
                    )
                    Toast.makeText(this@RegisterActivity, str, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                    intent.putExtra("firstName", it?.result?.user?.firstName)
                    intent.putExtra("lastName", it?.result?.user?.lastName)
                    intent.putExtra("email", it?.result?.user?.email)
                    startActivity(intent)
                    finish()
                }
            } else {
                Toast.makeText(this@RegisterActivity, it?.error.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun signUpClicked(view: View) {
        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()

        if (email.equals("") || password.equals("")) {
            Toast.makeText(this@RegisterActivity, "Enter email and password", Toast.LENGTH_LONG)
                .show()
        } else {
            val apiService = RestApiService(this@RegisterActivity)
            val data = RegisterPost(  email = email.toString(),
                password = password.toString() )

            apiService.registerPost(data) {
                println(it)
                if (it?.isSuccess == true) {
                    println(it?.result)
                    val sm = SessionManager(this@RegisterActivity)
                    it?.result?.let { it1 -> sm.saveAuthToken(it1.accessToken) }
                    var str: String = "Hoş Geldin, %s".format(it.result?.user?.email)
                    Toast.makeText(this@RegisterActivity, str, Toast.LENGTH_LONG).show()
                    val intent = Intent(this@RegisterActivity,MainActivity::class.java)
                    intent.putExtra("firstName", it?.result?.user?.firstName)
                    intent.putExtra("lastName", it?.result?.user?.lastName)
                    intent.putExtra("email", it?.result?.user?.email)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@RegisterActivity, it?.error.toString(), Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


