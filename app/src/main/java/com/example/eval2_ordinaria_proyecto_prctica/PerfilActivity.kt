package com.example.eval2_ordinaria_proyecto_prctica

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class PerfilActivity : AppCompatActivity() {

    private lateinit var imageViewPerfil: ImageView
    private lateinit var buttonAtras: Button
    private lateinit var textViewEmailPerfil: TextView
    private lateinit var textViewPasswordPerfil: TextView
    private lateinit var textViewUserPerfil: TextView

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_perfil)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

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
        val user = auth.currentUser
        user?.let {
            textViewEmailPerfil.text = user.email
            textViewPasswordPerfil.text = "********"

            val userId = user.uid
            db.collection("users").document(userId).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        val username = document.getString("username")
                        textViewUserPerfil.text = username
                    } else {
                        textViewUserPerfil.text = "Usuario no encontrado"
                    }
                }
                .addOnFailureListener { exception ->
                    textViewUserPerfil.text = "Error al cargar el usuario"
                }
        }
    }
}
