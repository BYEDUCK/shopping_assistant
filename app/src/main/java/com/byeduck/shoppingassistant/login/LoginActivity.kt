package com.byeduck.shoppingassistant.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.byeduck.shoppingassistant.MainActivity
import com.byeduck.shoppingassistant.databinding.ActivityLoginBinding
import com.byeduck.shoppingassistant.dialogs.LoadingDialog
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var loadingDialog: LoadingDialog
    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            goToMain()
        }
        binding = ActivityLoginBinding.inflate(layoutInflater)
        binding.apply {
            loginBtn.setOnClickListener { login() }
            signUpBtn.setOnClickListener { signUp() }
        }
        setContentView(binding.root)
        loadingDialog = LoadingDialog(this)
    }

    private fun login() {
        disableButtons()
        loadingDialog.startLoading()
        val email = binding.emailEditTxt.text.toString()
        val password = binding.passwordEditTxt.text.toString()
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    goToMain()
                } else {
                    Log.e("LOG IN", "Log in failed", it.exception)
                    Toast.makeText(applicationContext, "Log in failed", Toast.LENGTH_LONG).show()
                    enableButtons()
                    loadingDialog.stopLoading()
                }
            }
    }

    private fun signUp() {
        disableButtons()
        loadingDialog.startLoading()
        val email = binding.emailEditTxt.text.toString()
        val password = binding.passwordEditTxt.text.toString()
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(applicationContext, "Sign up completed", Toast.LENGTH_LONG)
                        .show()
                    binding.passwordEditTxt.text.clear()
                } else {
                    Log.e("SIGN UP", "Sign up failed", it.exception)
                    Toast.makeText(applicationContext, "Sign up failed!", Toast.LENGTH_LONG).show()
                }
                enableButtons()
                loadingDialog.stopLoading()
            }
    }

    private fun goToMain() {
        val goToMainIntent = Intent(applicationContext, MainActivity::class.java)
        startActivity(goToMainIntent)
    }

    private fun enableButtons() {
        binding.loginBtn.isEnabled = true
        binding.signUpBtn.isEnabled = true
    }

    private fun disableButtons() {
        binding.loginBtn.isEnabled = false
        binding.signUpBtn.isEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog.dismiss()
    }
}