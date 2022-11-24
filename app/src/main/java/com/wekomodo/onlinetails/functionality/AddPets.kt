package com.wekomodo.onlinetails.functionality

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.wekomodo.onlinetails.databinding.ActivityAddPetsBinding
import com.wekomodo.onlinetails.models.Pet
import java.util.*

class AddPets : AppCompatActivity() {
    private lateinit var binding: ActivityAddPetsBinding
    private lateinit var database: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddPetsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // ...
        database = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        val uid = mAuth.uid
        binding.floatingActionButton2.setOnClickListener{
            val name = binding.petName.text.toString()
            val type = binding.petType.text.toString()
            val breed = binding.petBreed.text.toString()
            val gender = binding.petGender.text.toString()
            val color = binding.petColor.text.toString()
            val birthday = binding.petBirthday.text.toString()
            if(name.isNotEmpty() && type.isNotEmpty() && breed.isNotEmpty() && gender.isNotEmpty() && color.isNotEmpty() && birthday.isNotEmpty() )
            {
                val pet = Pet(name,type,breed,gender,birthday,color)
                if (uid != null) {
                    database.child(uid).child(name).setValue(pet)
                    Toast.makeText(baseContext,"Data Uploaded",Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else
                Toast.makeText(baseContext,"Please fill all the fields",Toast.LENGTH_SHORT).show()
        }
        binding.petBirthday.setOnClickListener{
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            datePicker.show(supportFragmentManager,datePicker.toString())
            datePicker.addOnPositiveButtonClickListener{
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.time = Date(it)
                binding.petBirthday.setText("${calendar.get(Calendar.DAY_OF_MONTH)}- " +
                        "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}")
            }
        }

    }

}