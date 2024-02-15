package com.example.eval2_ordinaria_proyecto_prctica

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class PerfilActivity : AppCompatActivity() {


    //Añadir Un apartado de Editar Info no esencial
    //Añadir un par o tres cosas(direccion,Edad,CP)
    //Opcion Cambiar Avatar entre X opciones
    private lateinit var imageViewPerfil: ImageView
    private lateinit var buttonAtras: Button
    private lateinit var textViewEmailPerfil: TextView
    private lateinit var textViewPasswordPerfil: TextView
    private lateinit var textViewUserPerfil: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        imageViewPerfil = findViewById(R.id.imageViewPerfil)
        buttonAtras = findViewById(R.id.botonAtrasPerfil)
        textViewEmailPerfil = findViewById(R.id.textViewEmailPerfil)
        textViewPasswordPerfil = findViewById(R.id.textViewPasswordPerfil)
        textViewUserPerfil = findViewById(R.id.textViewUserPerfil)

        cargarInformacionUsuario()

        buttonAtras.setOnClickListener {
            finish()
        }
    }

    private fun cargarInformacionUsuario() {
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val currentUser = sharedPref.getString("currentUser", null)


        try {
            val fis = openFileInput("Usuarios.txt")
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            var userInfo: String?

            while (bufferedReader.readLine().also { userInfo = it } != null) {
                val parts = userInfo!!.split(",")
                if (parts.size >= 3 && parts[0] == currentUser) {
                    textViewUserPerfil.text = parts[0]
                    textViewEmailPerfil.text = parts[1]
                    textViewPasswordPerfil.text = parts[2]
                    break
                }
            }
            fis.close()
        } catch (e: Exception) {
            e.printStackTrace()
            textViewUserPerfil.text = "Error al cargar la información"
        }
    }

}
