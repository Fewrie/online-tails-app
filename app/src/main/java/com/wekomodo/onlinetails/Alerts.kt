package com.wekomodo.onlinetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.wekomodo.onlinetails.Adapters.AlertsItemAdapter
import com.wekomodo.onlinetails.databinding.ActivityAlertBinding
import com.wekomodo.onlinetails.models.Alert
import java.util.*
import kotlin.collections.ArrayList

class Alerts : AppCompatActivity(){
    private lateinit var binding: ActivityAlertBinding
    private lateinit var type : String
    private lateinit var mDatabase: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    private var uid : String? = ""
    private lateinit var alerts : MutableList<Alert>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mAuth = FirebaseAuth.getInstance()
        uid = mAuth.uid
        mDatabase = FirebaseDatabase.getInstance().reference
        val extras = intent.extras
        alerts = ArrayList()
        if(extras!=null)
            type = extras.getString("Alert").toString()

        if (uid != null) {
            mDatabase.child(uid!!).get().addOnSuccessListener {
                for (pet in it.children) {
                    val name = pet.key
                    val exists = pet.child("Alerts").exists()
                    for (alert in pet.child("Alerts").children) {
                        val task = alert.child("task").value.toString()
                        if (task.toLowerCase(Locale.ROOT) == type) {
                            val time = alert.child("time").value.toString()
                            val date = alert.child("date").value.toString()
                            name?.let { it1 -> Alert(it1, task, time, date) }
                                ?.let { it2 -> alerts.add(it2) }
                        }
                    }
                }
                Log.d("AlertData",alerts.toString())
                setAdapter(binding)
            }.addOnFailureListener{
            }
        }

    }

    private fun setAdapter(binding: ActivityAlertBinding) {
        val recyclerview = binding.alertRecyclerView
        val adapter = AlertsItemAdapter(alerts, this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerview.adapter = adapter
    }
}