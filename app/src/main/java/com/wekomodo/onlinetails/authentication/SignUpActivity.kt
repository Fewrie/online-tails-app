package com.wekomodo.onlinetails.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.wekomodo.onlinetails.DashboardActivity
import com.wekomodo.onlinetails.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySignUpBinding
    private lateinit var mAuth : FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        binding.signup.setOnClickListener{
            val email = binding.signupEmail.text.toString()
            val password = binding.signupPassword.text.toString()
            val passwordA = binding.signupPasswordAgain.text.toString()
            if(email.isEmpty() || password.isEmpty() || passwordA.isEmpty())
            {    Toast.makeText(baseContext,"Please don't leave fields empty",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(password != passwordA){
                Toast.makeText(baseContext,"Passwords don't match",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val user = mAuth.currentUser
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }
}