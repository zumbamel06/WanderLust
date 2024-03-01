package com.wanderlab.wanderlustcompanion

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.wanderlab.wanderlustcompanion.ui.login.LoginActivity


class WelcomeScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome_screen)

        val button = findViewById<View>(R.id.get_started_button) as Button
        button.setOnClickListener {
            // code to be executed when button is clicked
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}