package com.example.oliymahadkurslar.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.oliymahadkurslar.MainActivity
import com.example.oliymahadkurslar.databinding.ActivitySignInBinding
import com.example.oliymahadkurslar.databinding.ActivitySignUpBinding
import com.example.oliymahadkurslar.model.UserModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val db = Firebase.firestore
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)

        binding.goToSignIn.setOnClickListener {
            finish()
        }
        val name = binding.signUpName.text.toString().trim()
        val password = binding.signUpPassword.text.toString().trim()
        val email = binding.signUpPassword.text.toString().trim()
        val phone = binding.signUpPhoneNumber.text.toString().trim()
        binding.signUpBtn.setOnClickListener {
            if (binding.signUpName.text.toString().trim().isEmpty()) {
                showToast()
                return@setOnClickListener
            }
            if (binding.signUpPassword.text.toString().trim().length < 4) {
                showToast()
                return@setOnClickListener
            }
            if (binding.signUpPassword.text.toString().trim()
                    .isEmpty()
            ) {
                showToast()
                return@setOnClickListener
            }
            if (binding.signUpPhoneNumber.text.toString().trim().length < 7) {
                showToast()
                return@setOnClickListener
            }
            val name = binding.signUpName.text.toString().trim()
            val password = binding.signUpPassword.text.toString().trim()
            val email = binding.signUpEmail.text.toString().trim()
            val phone = binding.signUpPhoneNumber.text.toString().trim()

            val m = UserModel(
                userEmail = email,
                userName = name,
                userPhone = phone,
                userPassword = password
            )

            db.collection("users").add(m).addOnSuccessListener {
                val intent = Intent(this, MainActivity::class.java)
                val editor = sharedPreferences.edit()
                editor.putString("USER_NAME", name)
                editor.putBoolean("IS_LOGGED", true)
                editor.apply()
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                stopPb(binding)
                Toast.makeText(this, it.message.toString(), Toast.LENGTH_SHORT).show()
            }
        }


    }

    private fun showToast() {
        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
    }

    private fun startPb(binding: ActivitySignUpBinding) {
        binding.justText.visibility = View.GONE
        binding.startPb.visibility = View.VISIBLE
    }

    private fun stopPb(binding: ActivitySignUpBinding) {
        binding.justText.visibility = View.VISIBLE
        binding.startPb.visibility = View.GONE
    }
}