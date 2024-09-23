package com.example.tp3programacionavanzada2

import OpenHelper.SQLite_OpenHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    lateinit var txtRegistrarse: TextView
    lateinit var btnIniciarSesion: Button
    lateinit var etUsuario:  EditText
    lateinit var Contraseña:  EditText
    val  dbHelper = SQLite_OpenHelper(this, "BD1", null, 1)
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
        supportActionBar?.title = "Parking control"
        txtRegistrarse = findViewById<TextView>(R.id.tvRegistrarse)
        btnIniciarSesion =findViewById<Button>(R.id.btnIniciarSesion)
        txtRegistrarse.setOnClickListener{
            val i = Intent(applicationContext, RegistrarUsuario::class.java)
            startActivity(i)
        }

        btnIniciarSesion.setOnClickListener{
            etUsuario= findViewById<EditText>(R.id.etNombre)
            Contraseña= findViewById<EditText>(R.id.etContraseña)
            if (dbHelper.validarUsuario(etUsuario.text.toString(), Contraseña.text.toString())) {
                val intent = Intent(applicationContext, MenuInicio::class.java)

                intent.putExtra("Nombre", etUsuario.text.toString())

                startActivity(intent)
                Toast.makeText(this, "Usuario Valido", Toast.LENGTH_SHORT).show()
            } else {

                Toast.makeText(this, "Nombre de usuario o contraseña incorrectos.", Toast.LENGTH_SHORT).show()
            }

        }
    }
}