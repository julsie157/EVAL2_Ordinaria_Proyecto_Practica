package com.example.eval2_ordinaria_proyecto_prctica

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUser: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button


    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        mAuth = FirebaseAuth.getInstance()


        editTextUser = findViewById(R.id.editTextUserLogin)
        editTextPassword = findViewById(R.id.editTextPassLogin)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            loginUser()
        }

       val textViewRegister = findViewById<TextView>(R.id.textViewRegister)
        textViewRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser() {
        val username = editTextUser.text.toString().trim()
        val password = editTextPassword.text.toString().trim()

        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su usuario y contraseña.", Toast.LENGTH_SHORT).show()
            return
        }

        getEmailForUsername(username) { email ->
            email?.let {
                // Si el email fue encontrado, intenta iniciar sesión con Firebase Auth
                mAuth.signInWithEmailAndPassword(it, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        showToast("Inicio de sesión exitoso!")
                    } else {
                        Toast.makeText(this, "Autenticación fallida: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            } ?: run {
                // Si el email es null, muestra un mensaje de error
                Toast.makeText(this, "Nombre de usuario no encontrado", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getEmailForUsername(username: String, completion: (String?) -> Unit) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("username", username)
            .limit(1)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    completion(null) // No se encontró el usuario
                } else {
                    val email = documents.firstOrNull()?.getString("email")
                    completion(email)
                }
            }
            .addOnFailureListener {
                completion(null)
            }
    }


    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
