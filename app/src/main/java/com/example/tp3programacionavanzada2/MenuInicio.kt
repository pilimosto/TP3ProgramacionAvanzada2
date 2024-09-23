package com.example.tp3programacionavanzada2

import OpenHelper.SQLite_OpenHelper
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.tp3programacionavanzada2.databinding.ActivityMenuInicioBinding
import com.example.tp3programacionavanzada2.ui.CustomGridAdapter

class MenuInicio : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuInicioBinding

    private lateinit var tvUsuario: TextView
    private lateinit var tvCorreo: TextView
    private lateinit var nombre: String


    val helper = SQLite_OpenHelper(this, "BD1", null, 1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuInicioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMenuInicio.toolbar)

        val intent = intent
        nombre = intent.getStringExtra("Nombre") ?: ""

        val idUsuario = obtenerIdUsuario(nombre)
        cargarGrid(idUsuario.toString())



        binding.appBarMenuInicio.fab.setOnClickListener { view ->

            val inflater = layoutInflater
            val vistaDialogo = inflater.inflate(R.layout.dialogo, null)

            val dialogo = AlertDialog.Builder(this)
                .setView(vistaDialogo)
                .create()

            val editTextTime = vistaDialogo.findViewById<EditText>(R.id.editTextTime)
            val editTextMatricula = vistaDialogo.findViewById<EditText>(R.id.editTextTextPostalAddress)
            val textViewCancelar = vistaDialogo.findViewById<TextView>(R.id.textViewCancelar)
            val textViewRegistrar = vistaDialogo.findViewById<TextView>(R.id.textViewRegistrar)

            textViewCancelar.setOnClickListener {
                dialogo.dismiss()
            }

            textViewRegistrar.setOnClickListener {
                helper.abrir()

                    helper.insertarParq(editTextMatricula.text.toString(), editTextTime.text.toString(), idUsuario)

                    Toast.makeText(applicationContext, "Parqueo almacenado con éxito", Toast.LENGTH_SHORT).show()
                cargarGrid(idUsuario.toString())

                helper.cerrar()
                dialogo.dismiss()
            }

            dialogo.show()

        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_menu_inicio)

        val headerView = navView.getHeaderView(0)
        tvUsuario = headerView.findViewById(R.id.TextViewUsuario)
        tvCorreo = headerView.findViewById(R.id.TextViewCorreo)


        consultarDatos()


        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_inicio, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.itemCerrarSesion -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_menu_inicio)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun consultarDatos(){
        val db = helper.readableDatabase
        val parametros = arrayOf(nombre)
        val campos = arrayOf("Nombre", "Correo")

        try {
            val cursor = db.query("usuarios", campos, "Nombre=?", parametros, null, null, null)
            cursor.moveToFirst()
            tvUsuario.text = cursor.getString(0)
            tvCorreo.text = cursor.getString(1)
            cursor.close()
        } catch (e: Exception){
            Toast.makeText(applicationContext, "El registro no existe", Toast.LENGTH_SHORT).show()
        }
    }

    fun obtenerIdUsuario(nombre: String): Int {
        val db = helper.readableDatabase
        var usuarioId = -1

        val query = "SELECT _ID FROM usuarios WHERE Nombre = ?"
        val cursor = db.rawQuery(query, arrayOf(nombre))

        try {
            if (cursor.moveToFirst()) {
                usuarioId = cursor.getInt(0)
                cursor.close()
            }
        }catch (e: Exception){
            Toast.makeText(applicationContext, "El registro no existe", Toast.LENGTH_SHORT).show()
        }

        return usuarioId
    }
    private fun cargarGrid(id:String){
        val dataList = helper.getMatriculaYTiempo(id)
        val gridView: GridView = findViewById(R.id.GVParqueos)
        val pairedList = mutableListOf<Pair<String, String>>()
        for (i in dataList.indices step 2) {
            if (i + 1 < dataList.size) {
                pairedList.add(Pair(dataList[i], dataList[i + 1]))
            }
        }

        val adapter = CustomGridAdapter(this, pairedList)
        gridView.adapter = adapter
    }




}