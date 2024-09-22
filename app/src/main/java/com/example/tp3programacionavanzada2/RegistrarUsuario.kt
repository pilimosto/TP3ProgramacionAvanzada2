package com.example.tp3programacionavanzada2

import OpenHelper.SQLite_OpenHelper
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegistrarUsuario : AppCompatActivity() {

    lateinit var btnAceptar: Button
    lateinit var txtNombre:EditText
    lateinit var txtCorreo:EditText
    lateinit var txtContrasena:EditText

    val helper = SQLite_OpenHelper(this, "BD1", null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar_usuario)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        btnAceptar = findViewById(R.id.buttonAceptar)
        txtNombre = findViewById(R.id.editTextNombreUsuario)
        txtCorreo = findViewById(R.id.editTextCorreo)
        txtContrasena= findViewById(R.id.editTextContrasenia)

        btnAceptar.setOnClickListener{
            helper.abrir()
            if (helper.validarNombreUsuario(txtNombre.text.toString())){
            helper.insertarReg(txtNombre.text.toString(), txtCorreo.text.toString(), txtContrasena.text.toString())
            helper.cerrar()

            Toast.makeText(applicationContext, "Registro almacenado con éxito", Toast.LENGTH_SHORT).show()


            val i = Intent(applicationContext, MainActivity::class.java)
            startActivity(i)}else{
                Toast.makeText(applicationContext, "Nombre Usuario Invalido", Toast.LENGTH_SHORT).show()

            }
        }




    }
}