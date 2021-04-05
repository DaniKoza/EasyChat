package com.danikoza.easychat

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import com.danikoza.easychat.databinding.ActivityRegisterBinding
import com.danikoza.easychat.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.*

private const val REQUEST_CODE: Int = 100

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private var selectedPhotoUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth

        // Click listeners
        binding.alreadyHaveAccountLabelRegister.setOnClickListener { goToLogin() }
        binding.registerButtonRegister.setOnClickListener { performRegister() }
        binding.selectPhotoButtonRegister.setOnClickListener { chooseProfilePhoto() }

        // EditText Listeners
        binding.passwordEditTextRegister.doAfterTextChanged { checkAllTextFields() }
        binding.emailEditTextRegister.doAfterTextChanged { checkAllTextFields() }
        binding.userNameEditTextRegister.doAfterTextChanged { checkAllTextFields() }
    }

    private fun checkAllTextFields() {
        binding.registerButtonRegister.isEnabled =
            binding.emailEditTextRegister.text.isNotBlank() &&
                    binding.userNameEditTextRegister.text.isNotBlank() &&
                    binding.passwordEditTextRegister.text.isNotBlank()
    }

    private fun chooseProfilePhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE) {
            selectedPhotoUri = data?.data
            binding.selectPhotoButtonRegister.isVisible = false
            binding.circleProfilePicture.setImageURI(selectedPhotoUri)
            binding.circleProfilePicture.isVisible = true
        }
    }

    private fun goToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun performRegister() {
        binding.progressBarRegister.isVisible = true
        val email = binding.emailEditTextRegister.text.toString()
        val password = binding.passwordEditTextRegister.text.toString()
        val userName = binding.userNameEditTextRegister.text.toString()

        if (email.isEmpty() || password.isEmpty() || userName.isEmpty()) {
            Toast.makeText(this, "Please fill all details", Toast.LENGTH_SHORT).show()
            binding.progressBarRegister.isVisible = false
            return
        }

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("Registration", "createUserWithEmail:success")
                    uploadImageToFirebaseStorage()
                } else {
                    // If sign in fails, display a message to the user.
                    Log.d(
                        "Registration", "createUserWithEmail:failure", it.exception
                    )
                    Toast.makeText(
                        baseContext,
                        "Authentication failed: ${it.exception?.message}",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }
    }

    private fun uploadImageToFirebaseStorage() {
        if (selectedPhotoUri == null) return
        val fileName = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("/images/$fileName")

        ref.putFile(selectedPhotoUri!!)
            .addOnSuccessListener { it ->
                Log.d("Register", "Successfully uploaded image: ${it.metadata?.path}")

                ref.downloadUrl.addOnSuccessListener { url ->
                    saveUserToFirebaseRealtimeDatabase(url.toString())
                }
            }
    }

    private fun saveUserToFirebaseRealtimeDatabase(profileImageUrl: String) {
        val ref = FirebaseDatabase.getInstance().getReference("/users/${auth.uid}")
        val userName = binding.userNameEditTextRegister.text.toString()
        val user = User(auth.uid.toString(), userName, profileImageUrl)

        ref.setValue(user)
            .addOnSuccessListener {
                binding.progressBarRegister.isVisible = false
                Log.d("Registration", "Finally user is saved to realtime DB")
                val intent = Intent(this, LatestMessagesActivity::class.java)
                startActivity(intent)
                finish()
            }
    }
}