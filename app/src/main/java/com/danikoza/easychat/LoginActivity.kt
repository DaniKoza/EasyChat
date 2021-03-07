package com.danikoza.easychat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.danikoza.easychat.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.loginButtonLogin.setOnClickListener { performLogin() }
        binding.registerLabelLogin.setOnClickListener { goToRegistration() }


//      Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            Log.d("Login", "Already signed in")
            goToLatestMessages()
        }

    }

    private fun goToLatestMessages() {
        val intent = Intent(this, LatestMessagesActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun goToRegistration() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun performLogin() {
        val email = binding.emailEdittextLogin.text.toString()
        val password = binding.passwordEdittextLogin.text.toString()


        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter email/password", Toast.LENGTH_LONG).show()
            return
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                Log.d("Login", "loginWithEmailAndPassword: success")
                goToLatestMessages()
            }
            .addOnFailureListener {
                Log.d("Login", "loginWithEmailAndPassword: failed")
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_LONG).show()
            }


    }
}