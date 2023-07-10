package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import database.CompanyDatabase
import database.repositories.CompanyRepository
import database.repositories.GigRepository
import database.repositories.JobRepository
import database.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AdminDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)
        
        
        //repositories
           val repository = CompanyRepository(CompanyDatabase.getInstance(this))
        val repositoryJob = JobRepository(CompanyDatabase.getInstance(this))
        val repositoryUser = UserRepository(CompanyDatabase.getInstance(this))
        val repositoryGig = GigRepository(CompanyDatabase.getInstance(this))
        
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllCompanies()
            val dataCompCount = repository.getAllCompanyCounts()
            val dataJobCount = repositoryJob.getAllJobCounts()
            val dataUserCount = repositoryUser.getUserCount()
            val dataGigCount = repositoryGig.getGigCount()

            val tvCompCount = findViewById<TextView>(R.id.txtCompanyValue)
            val tvJobCount = findViewById<TextView>(R.id.txtJobsValue)
            val tvUserCount = findViewById<TextView>(R.id.txtUserValue)
            val tvGigCount = findViewById<TextView>(R.id.txtFreelanceValue)
            tvCompCount.text = dataCompCount.toString()
            tvJobCount.text = dataJobCount.toString()
            tvUserCount.text = dataUserCount.toString()
            tvGigCount.text = dataGigCount.toString()

//            val data1 = repository.getAllCompaniesByAddress("Kandy")
//            adapter.setData(data, ui)
        }


        val btnViewCompanyreq = findViewById<Button>(R.id.btnViewCompanyRequests)
        btnViewCompanyreq.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val btnGoAdminProfile = findViewById<Button>(R.id.btnGoAdminProfile)
        btnGoAdminProfile.setOnClickListener {
            var intent = Intent(this, AdminProfile::class.java)
            startActivity(intent)
            finish()
        }

        val btnViewGigs = findViewById<Button>(R.id.btnViewGigs)
        btnViewGigs.setOnClickListener {
            var intent = Intent(this, AllGigs::class.java)
            startActivity(intent)
            finish()
        }
    }
}
