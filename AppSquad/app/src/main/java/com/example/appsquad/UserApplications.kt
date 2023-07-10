package com.example.appsquad

import adapters.JobApplicationAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.CompanyDatabase
import database.repositories.ApplicationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserApplications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_applications)

        val recyclerView: RecyclerView = findViewById(R.id.rvUserApplications)
        val adapter = JobApplicationAdapter()
        val repository = ApplicationRepository(CompanyDatabase.getInstance(this))
        val ui = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("user", null)

        CoroutineScope(Dispatchers.IO).launch {
            val data = cookies?.let { repository.getUserApplications(it.toInt()) }

            if (data != null) {
                adapter.setData(data ,ui)
            }
        }


        val btnGoBack = findViewById<Button>(R.id.btnGoBackUserApplications)
        btnGoBack.setOnClickListener {
            var intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}