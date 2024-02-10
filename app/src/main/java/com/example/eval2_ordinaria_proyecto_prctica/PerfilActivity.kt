package com.example.eval2_ordinaria_proyecto_prctica

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader

class PerfilActivity : AppCompatActivity() {

    private lateinit var imageViewPerfil: ImageView
    private lateinit var buttonAtras: Button
    private lateinit var textViewEmailPerfil: TextView
    private lateinit var textViewPasswordPerfil: TextView
    private lateinit var textViewUserPerfil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        imageViewPerfil = findViewById(R.id.imageViewPerfil)
        buttonAtras = findViewById(R.id.botonAtrasInfo)
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

        // Lee el archivo y busca la línea con la información del usuario actual
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
                    textViewPasswordPerfil.text = "**********"
                    break // Sal del bucle una vez que encuentres la información del usuario
                }
            }
            fis.close()
        } catch (e: Exception) {
            e.printStackTrace()
            textViewUserPerfil.text = "Error al cargar la información"
        }
    }

}
