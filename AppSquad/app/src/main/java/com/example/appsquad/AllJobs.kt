package com.example.appsquad

import adapters.JobAdapter
import adapters.JobUserAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.CompanyDatabase
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllJobs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_jobs)

        val recyclerView: RecyclerView = findViewById(R.id.rvAllJobs)
        val adapter = JobUserAdapter()
        val repository = JobRepository(CompanyDatabase.getInstance(this))
        val ui = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllJobs()

            if (data != null) {
                adapter.setData(data ,ui)
            }
        }


        val btnGoback = findViewById<Button>(R.id.btnGoBackAllJobs)
        btnGoback.setOnClickListener {
            var intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}