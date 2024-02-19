package com.example.eval2_ordinaria_proyecto_prctica

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.BufferedReader
import java.io.InputStreamReader

class PerfilActivity : AppCompatActivity() {

    private lateinit var imagen: ImageView
    private lateinit var recycler: RecyclerView
    private lateinit var buttonAtras: Button
    private lateinit var buttonAdicional: Button
    private lateinit var textViewElegir: TextView
    private lateinit var textViewEmailPerfil: TextView
    private lateinit var textViewPasswordPerfil: TextView
    private lateinit var textViewUserPerfil: TextView

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_perfil)

        imagen = findViewById(R.id.imageViewPerfil)
        recycler = findViewById(R.id.recyclerPerfil)
        buttonAtras = findViewById(R.id.botonAtrasPerfil)
        buttonAdicional = findViewById(R.id.botonInfoAdicional)
        textViewEmailPerfil = findViewById(R.id.textViewEmailPerfil)
        textViewPasswordPerfil = findViewById(R.id.textViewPasswordPerfil)
        textViewUserPerfil = findViewById(R.id.textViewUserPerfil)
        textViewElegir = findViewById(R.id.textViewElegir)

        cargarAvatarSeleccionado()
        cargarInformacionUsuario()


        recycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = Adaptador(photos) { photo ->
            imagen.setImageResource(photo)

            val sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE)
            sharedPreferences.edit().putInt("photoId", photo).apply()
            setResult(Activity.RESULT_OK)
        }
        recycler.adapter = adapter


        buttonAtras.setOnClickListener {
            finish()
        }

        buttonAdicional.setOnClickListener{
            val intent = Intent(this, InformacionExtraUsuario::class.java)
            val sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE)
            val photoId = sharedPreferences.getInt("photoId", R.drawable.perfil)
            intent.putExtra("photoId", photoId)
            startActivity(intent)
        }


    }

    private val photos = listOf(
        R.drawable.avatar1, R.drawable.avatar2, R.drawable.avatar3, R.drawable.avatar4, R.drawable.avatar5, R.drawable.avatar6,
    )

    private fun cargarAvatarSeleccionado() {
        val sharedPreferences = getSharedPreferences("Perfil", Context.MODE_PRIVATE)
        val photoId = sharedPreferences.getInt("photoId", R.drawable.perfil)
        imagen.setImageResource(photoId)
    }

    private fun cargarInformacionUsuario() {
        val sharedPref = getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
        val currentUser = sharedPref.getString("currentUser", null)

        try {
            val fis = openFileInput("FicheroUsuarios.txt")
            val isr = InputStreamReader(fis)
            val bufferedReader = BufferedReader(isr)
            var userInfo: String?

            while (bufferedReader.readLine().also { userInfo = it } != null) {
                val parts = userInfo!!.split(",")
                if (parts.size >= 3 && parts[0] == currentUser) {
                    textViewUserPerfil.text = parts[0]
                    textViewEmailPerfil.text = parts[1]
                    textViewPasswordPerfil.text = "*******"
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
