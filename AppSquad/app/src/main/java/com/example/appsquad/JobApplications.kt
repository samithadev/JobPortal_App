package com.example.appsquad

import adapters.ApplicationAdapater
import adapters.JobAdapter
import adapters.JobApplicationAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.CompanyDatabase
import database.entities.Application
import database.repositories.ApplicationRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class JobApplications : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_job_applications)

        val recyclerView: RecyclerView = findViewById(R.id.rvJobApplications)
        val adapter = ApplicationAdapater()
        val repository = ApplicationRepository(CompanyDatabase.getInstance(this))
        val ui = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        val id = intent.getStringExtra("jobId").toString()
        val jid = id.toInt()

        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getJobApplications(jid)

            if (data != null) {
                adapter.setData(data ,ui)
            }
        }

        val btnGoback = findViewById<Button>(R.id.btnGoBackMyJobs)
        btnGoback.setOnClickListener {
            var intent = Intent(this, CompanyJobs::class.java)
            startActivity(intent)
            finish()
        }
    }
}