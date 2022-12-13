package com.wekomodo.onlinetails.functionality

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat.is24HourFormat
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.wekomodo.onlinetails.R
import com.wekomodo.onlinetails.databinding.ActivityAddAlertBinding
import com.wekomodo.onlinetails.models.Alert
import com.wekomodo.onlinetails.models.Pet
import java.util.*
import kotlin.collections.ArrayList

class AddAlert : AppCompatActivity() {
    private lateinit var binding : ActivityAddAlertBinding
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private lateinit var uid : String
    private lateinit var petnames : MutableList<String>
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mDatabase = FirebaseDatabase.getInstance().reference
        mAuth = FirebaseAuth.getInstance()
        uid = mAuth.uid.toString()
        setAdapters(binding)
        binding.groomingPetDate.setOnClickListener {
            val datePicker =
                MaterialDatePicker.Builder.datePicker()
                    .setTitleText("Select date")
                    .build()
            datePicker.show(supportFragmentManager, datePicker.toString())
            datePicker.addOnPositiveButtonClickListener {
                val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
                calendar.time = Date(it)
                binding.groomingPetDate.setText(
                    "${calendar.get(Calendar.DAY_OF_MONTH)}- " +
                            "${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.YEAR)}"
                )
            }
        }
        binding.groomingPetTime.setOnClickListener {
            val isSystem24Hour = is24HourFormat(this)
            val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H
            val picker =
                MaterialTimePicker.Builder()
                    .setTimeFormat(clockFormat)
                    .setHour(12)
                    .setMinute(10)
                    .setTitleText("Select Appointment time")
                    .build()
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                val time = picker.hour.toString() + " : " + picker.minute.toString()
                binding.groomingPetTime.setText(time)
            }
        }
        binding.addAlert.setOnClickListener {
            val name = binding.groomingPetName.text.toString()
            val task = binding.groomingPetTask.text.toString()
            val date = binding.groomingPetDate.text.toString()
            val time = binding.groomingPetTime.text.toString()
            if (name.isNotEmpty() && task.isNotEmpty() && date.isNotEmpty() && time.isNotEmpty()) {
                val alert = Alert(task = task, time = time, date = date)
                if (uid != null) {
                    mDatabase.child(uid).child(name).child("Alerts").push().setValue(alert)
                    Toast.makeText(baseContext,"Data Uploaded", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
            else
                Toast.makeText(this,"Fill out the fields",Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapters(binding: ActivityAddAlertBinding) {
        petnames = ArrayList()
        if (uid != null) {
            mDatabase.child(uid).get().addOnSuccessListener {
                petnames.clear()
                for (pet in it.children)
                {
                    pet.key?.let { it1 -> petnames.add(it1) }
                }

                Log.i("firebase", "Got value ${it.value}")
            }.addOnFailureListener{
                Log.e("firebase", "Error getting data", it)
            }
        }
        val task = arrayOf(
            "Appointment",
            "Medicine",
            "Grooming",
            "Supplies",
        )
        var petName: ArrayAdapter<String> = ArrayAdapter<String>(this, R.layout.dropdown_menu,
            this.petnames
        )
        val petTask = ArrayAdapter(this, R.layout.dropdown_menu, task)
        binding.groomingPetName.setAdapter(petName)
        binding.groomingPetTask.setAdapter<ArrayAdapter<String>>(petTask)
    }

}