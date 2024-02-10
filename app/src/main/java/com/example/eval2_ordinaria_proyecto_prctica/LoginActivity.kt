package com.example.eval2_ordinaria_proyecto_prctica

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextUsername: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button
    private lateinit var textViewRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Asegúrate de tener el nombre correcto de tu layout aquí

        // Inicializa Firebase Auth
        auth = FirebaseAuth.getInstance()

        // Referencias a las vistas
        editTextUsername = findViewById(R.id.editTextUserLogin)
        editTextPassword = findViewById(R.id.editTextPassLogin)
        buttonLogin = findViewById(R.id.buttonLogin)
        textViewRegister = findViewById(R.id.textViewRegister)

        buttonLogin.setOnClickListener {
            val username = editTextUsername.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            signInWithUsername(username, password)
        }

        textViewRegister.setOnClickListener {
            // Navegar a RegistroActivity
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun signInWithUsername(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Usuario y contraseña no pueden estar vacíos", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("LoginActivity", "Buscando usuario: $username")

        FirebaseFirestore.getInstance().collection("users")
            .whereEqualTo("username", username)
            .get()
            .addOnSuccessListener { documents ->
                if (documents.isEmpty) {
                    Log.d("LoginActivity", "Usuario no encontrado en Firestore.")
                    Toast.makeText(this, "Usuario no encontrado.", Toast.LENGTH_SHORT).show()
                } else {
                    val email = documents.first().getString("email") ?: ""
                    Log.d("LoginActivity", "Correo electrónico encontrado: $email")
                    signInWithEmail(email, password)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("LoginActivity", "Error al buscar el usuario: ${exception.message}")
                Toast.makeText(this, "Error al buscar el usuario.", Toast.LENGTH_SHORT).show()
            }
    }


    private fun signInWithEmail(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Inicio de sesión exitoso, actualizar UI
                    val intent = Intent(this, PrincipalActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    when (task.exception) {
                        is FirebaseAuthInvalidUserException -> Toast.makeText(baseContext, "Usuario no existe.", Toast.LENGTH_SHORT).show()
                        is FirebaseAuthInvalidCredentialsException -> Toast.makeText(baseContext, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show()
                        else -> Toast.makeText(baseContext, "Error de autenticación.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
    }
}
