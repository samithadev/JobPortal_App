package com.example.appsquad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import database.CompanyDatabase
import database.repositories.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CompanyDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_dashboard)

        val btnCompanyProfile = findViewById<Button>(R.id.btnCompanyProfile)
        btnCompanyProfile.setOnClickListener {
            var intent = Intent(this, CompanyProfile::class.java)
            startActivity(intent)
            finish()
        }
        

        val repository = CompanyRepository(CompanyDatabase.getInstance(this))


        val btnCompanyMyJobs = findViewById<Button>(R.id.btnCompanyMyJobs)

        btnCompanyMyJobs.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
            val cookies = sharedPreferences.getString("company", null)

            if (cookies != null) {
                checkApprovalMyJobs(repository , this ,cookies )

            }

        }

        val btnCreatJob = findViewById<Button>(R.id.btnCreateJob)

        btnCreatJob.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
            val cookies = sharedPreferences.getString("company", null)

            if (cookies != null) {
                checkApprovalCreateJobs(repository , this ,cookies )

            }
        }

        val btnGigs = findViewById<Button>(R.id.btnAllGigs)
        btnGigs.setOnClickListener {
            var intent = Intent(this, AllGigs::class.java)
            startActivity(intent)
            finish()

        }




    }

    fun checkApprovalMyJobs(repository: CompanyRepository , context: Context , cookies:String){
        GlobalScope.launch(Dispatchers.IO) {

            val data = repository.getCompanyDetail(cookies.toInt())

            if (data.approved.equals("approved")) {
                var intent = Intent(context, CompanyJobs::class.java)
                startActivity(intent)
                finish()
            }else {
                var intent = Intent(context, PendingNotice::class.java)
                startActivity(intent)
                finish()
            }



        }

    }

    fun checkApprovalCreateJobs(repository: CompanyRepository , context: Context , cookies:String){
        GlobalScope.launch(Dispatchers.IO) {

            val data = repository.getCompanyDetail(cookies.toInt())

            if (data.approved.equals("approved")) {
                var intent = Intent(context, CreateJob::class.java)
                startActivity(intent)
                finish()
            }else {
                var intent = Intent(context, PendingNotice::class.java)
                startActivity(intent)
                finish()
            }



        }

    }
}
