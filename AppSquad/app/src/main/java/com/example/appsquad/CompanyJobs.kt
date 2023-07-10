package com.example.appsquad

import adapters.CompanyAdapter
import adapters.JobAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.CompanyDatabase
import database.repositories.CompanyRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CompanyJobs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_jobs)

        val recyclerView: RecyclerView = findViewById(R.id.rvCompanyJobs)
        val adapter = JobAdapter()
        val repository = JobRepository(CompanyDatabase.getInstance(this))
        val ui = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("company", null)

        CoroutineScope(Dispatchers.IO).launch {
            val data = cookies?.let { repository.getCompanyJobs(it.toInt()) }

            if (data != null) {
                adapter.setData(data ,ui)
            }
        }
        

        val btngoback = findViewById<Button>(R.id.btnGoCompDashfromjobs)
        btngoback.setOnClickListener {
            var intent = Intent(this, LoginChoose::class.java)
            startActivity(intent)
            finish()
        }

    }
}
