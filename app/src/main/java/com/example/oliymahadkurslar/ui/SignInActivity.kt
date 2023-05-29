package com.example.oliymahadkurslar.ui

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.oliymahadkurslar.MainActivity
import com.example.oliymahadkurslar.R
import com.example.oliymahadkurslar.databinding.ActivitySignInBinding
import com.example.oliymahadkurslar.databinding.ActivitySignUpBinding
import com.example.oliymahadkurslar.model.UserModel
import com.example.oliymahadkurslar.ui.vm.SignInVm
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val viewModel: SignInVm by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences("my_preferences", Context.MODE_PRIVATE)
        var list = ArrayList<UserModel>()
        binding.goToSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
        viewModel.getAllUsers()
        viewModel.usersList.observe(this) {
            list = it as ArrayList<UserModel>
        }

        binding.signInBtn.setOnClickListener {
            if (binding.signInName.text.toString().trim().isEmpty()) {
                showToast()
                return@setOnClickListener
            }
            if (binding.signInPassword.text.toString().trim().length < 4) {
                showToast()
                return@setOnClickListener
            }
            startPb(binding)
            val name = binding.signInName.text.toString().trim()
            val password = binding.signInPassword.text.toString().trim()

            list.forEach {
                if (it.userName == name && it.userPassword == password) {
                    val intent = Intent(this, MainActivity::class.java)
                    val editor = sharedPreferences.edit()
                    editor.putString("USER_NAME", name)
                    editor.putBoolean("IS_LOGGED", true)
                    editor.apply()
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                    stopPb(binding)
                }
            }
        }
    }


    private fun showToast() {
        Toast.makeText(this, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
    }

    private fun startPb(binding: ActivitySignInBinding) {
        binding.justText.visibility = View.GONE
        binding.startPb.visibility = View.VISIBLE
    }

    private fun stopPb(binding: ActivitySignInBinding) {
        binding.justText.visibility = View.VISIBLE
        binding.startPb.visibility = View.GONE
    }
}