package com.example.mychatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.chatapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp

class LoginActivity : AppCompatActivity() {
    // Create the Variables for the inputs.
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        // Assign the inputs to the variables.
        val emailInput = binding.emailEditText
        val passwordInput = binding.passwordEditText
        val loginButton = binding.loginButton
        val signupButton = binding.registerButton

        loginButton.setOnClickListener {
            // Get the email and password from the inputs.
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                showError("Email and Password cannot be empty")
                return@setOnClickListener
            }

            if (!isValidEmail(email)) {
                showError("Invalid Email Format")
                return@setOnClickListener
            }

            Log.i("Test Credentials", "Email: $email and Password: $password")
            loginUser(email, password)
        }

        signupButton.setOnClickListener {
            // Redirect to the Signup Activity
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    // Function to validate email format
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        return email.matches(emailPattern.toRegex())
    }

    // Function to show error message in a notification banner
    private fun showError(message: String) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.TOP, 0, 0)
        toast.show()
    }

    // Function to login user with Firebase Authentication
    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success
                    showError("Login Successful.")
                    startActivity(Intent(this, SaluteActivity::class.java))
                } else {
                    // If sign-in fails, display a message to the user.
                    showError("Authentication failed.")
                }
            }
    }
}