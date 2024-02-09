package com.example.eval2_ordinaria_proyecto_prctica

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistroActivity : AppCompatActivity() {

    private lateinit var editTextUsername: EditText
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonConfirmar: Button

    // Instancia de FirebaseAuth
    private lateinit var mAuth: FirebaseAuth

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializa Firebase Auth
        mAuth = FirebaseAuth.getInstance()

        // Inicializa vistas
        editTextUsername = findViewById(R.id.editTextUserRegister)
        editTextEmail = findViewById(R.id.editTextEmailRegister)
        editTextPassword = findViewById(R.id.editTextPasswordRegister)
        buttonConfirmar = findViewById(R.id.buttonConfirmar)

        buttonConfirmar.setOnClickListener {
            val email = editTextEmail.text.toString().trim()
            val password = editTextPassword.text.toString().trim()
            val username = editTextUsername.text.toString().trim()

            if (validateForm(email, username, password)) {
                registerUser(email, password, username)
            }
        }

    }

    private fun validateForm(email: String, username: String, password: String): Boolean {
        // Validación del correo electrónico
        if (email.isEmpty()) {
            Toast.makeText(this, "El correo electrónico es obligatorio.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validación del nombre de usuario
        if (username.isEmpty()) {
            Toast.makeText(this, "El nombre de usuario es obligatorio.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (username.length < 4) {
            Toast.makeText(this, "El nombre de usuario debe tener al menos 4 caracteres.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!username.matches("^[A-Za-z]+$".toRegex())) {
            Toast.makeText(this, "El nombre de usuario solo puede contener letras.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Validaciones de la contraseña
        if (password.isEmpty()) {
            Toast.makeText(this, "La contraseña es obligatoria.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!password.matches(".*[A-Z].*".toRegex())) {
            Toast.makeText(this, "La contraseña debe contener al menos una letra mayúscula.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!password.matches(".*[0-9].*".toRegex())) {
            Toast.makeText(this, "La contraseña debe contener al menos un número.", Toast.LENGTH_SHORT).show()
            return false
        }

        // Si todas las validaciones son exitosas
        return true
    }



    private fun registerUser(email: String, password: String, username: String) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val userId = mAuth.currentUser?.uid // Obtiene el ID del usuario recién creado
                val userMap = hashMapOf(
                    "username" to username,
                    "email" to email
                )
                userId?.let {
                    FirebaseFirestore.getInstance().collection("users").document(it)
                        .set(userMap)
                        .addOnSuccessListener {
                            Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                            // Aquí puedes redirigir al usuario a la pantalla principal o realizar otras acciones necesarias
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(this, "Error al guardar el usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                }
            } else {
                // Si el registro falla, muestra un mensaje al usuario.
                Toast.makeText(this, "Registro fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

