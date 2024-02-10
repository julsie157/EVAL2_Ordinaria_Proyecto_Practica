package com.example.eval2_ordinaria_proyecto_prctica

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistroActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var editTextEmail: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var editTextUser: EditText
    private lateinit var buttonConfirmar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        // Inicializar Firebase Auth
        auth = Firebase.auth

        // Referencias a las vistas
        editTextEmail = findViewById(R.id.editTextEmailRegister)
        editTextPassword = findViewById(R.id.editTextPasswordRegister)
        editTextUser = findViewById(R.id.editTextUserRegister)
        buttonConfirmar = findViewById(R.id.buttonConfirmar)

        buttonConfirmar.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()
            val username = editTextUser.text.toString()

            if (validateForm(email, password, username)) {
                registerUser(email, password)
            }
        }
    }

    private fun validateForm(email: String, password: String, username: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Ingrese un correo electrónico válido.", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6 || !password.any { it.isDigit() } || !password.any { it.isUpperCase() }) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres, una mayúscula y un número.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun registerUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Registro exitoso, navegar a LoginActivity
                    Toast.makeText(this, "Registro exitoso.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                } else {
                    // Si el registro falla, muestra un mensaje al usuario.
                    Toast.makeText(this, "Registro fallido: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
