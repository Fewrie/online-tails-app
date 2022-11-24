package com.wekomodo.onlinetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wekomodo.onlinetails.databinding.ActivityMyPetsBinding
import com.wekomodo.onlinetails.functionality.AddPets

class MyPets : AppCompatActivity() {
    private lateinit var binding : ActivityMyPetsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.addNewPet.setOnClickListener{
            val intent = Intent(this@MyPets, AddPets::class.java)
            startActivity(intent)
        }

    }
}