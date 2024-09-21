package com.example.tp3programacionavanzada2

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var txtRegistrarse: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val toolbar:Toolbar=findViewById<Toolbar>(R.id.miToolBar)
        setSupportActionBar(toolbar)
        txtRegistrarse = findViewById<TextView>(R.id.tvRegistrarse)

        txtRegistrarse.setOnClickListener{
            val i = Intent(applicationContext, RegistrarUsuario::class.java)
            startActivity(i)
        }
    }
}