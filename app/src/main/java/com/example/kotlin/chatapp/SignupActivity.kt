package com.example.mychatapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.kotlin.chatapp.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.FirebaseApp

class SignupActivity : AppCompatActivity() {
    // Create the Variables for the inputs.
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySignupBinding

    public override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()

        setContentView(binding.root)

        // Assign the inputs to the variables.
        val emailInput = binding.usernameEditText
        val passwordInput = binding.passwordEditText
        val loginButton = binding.loginButton

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
            signUp(email, password)
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

    private fun reload() {
        // This function can be used to update the UI when the user is logged in
        Toast.makeText(this, "User is already logged in", Toast.LENGTH_SHORT).show()
    }

    private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign-in success
                    showError("Authentication Successful.")
                    startActivity(Intent(this, SaluteActivity::class.java))
                } else {
                    // If sign-in fails, display a message to the user.
                    showError("Authentication failed.")
                }
            }
    }
}