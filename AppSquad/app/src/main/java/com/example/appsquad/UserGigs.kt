package com.example.appsquad

import adapters.GigUserAdapter
import adapters.JobAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.CompanyDatabase
import database.repositories.GigRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserGigs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_gigs)

        val recyclerView: RecyclerView = findViewById(R.id.rvUserGigs)
        val adapter = GigUserAdapter()
        val repository = GigRepository(CompanyDatabase.getInstance(this))
        val ui = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("user", null)

        CoroutineScope(Dispatchers.IO).launch {
            val data = cookies?.let { repository.getUserGigs(it.toInt()) }

            if (data != null) {
                adapter.setData(data ,ui)
            }
        }

        val btnGoback = findViewById<Button>(R.id.btnGoBackFromUserGigs)
        btnGoback.setOnClickListener {
            var intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}