package com.projetogrupo8.taskmanager.Activitys

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.projetogrupo8.taskmanager.R

class MainActivity : AppCompatActivity() {

    private lateinit var button: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setupButton()
    }

    private fun setupButton() {
        button = findViewById(R.id.btnLogin)
        button.setOnClickListener {
            handleButtonClick()
        }
    }

    private fun handleButtonClick() {
        val intent = Intent(applicationContext, SecondActivity::class.java)
        startActivity(intent)
    }
}