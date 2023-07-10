package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import database.CompanyDatabase
import database.repositories.CompanyRepository
import database.repositories.JobRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class EditJob : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_job)

        val id = intent.getStringExtra("jobId").toString()
        val jid = id.toInt()
        

        val repositoryJ = JobRepository(CompanyDatabase.getInstance(this))

        val btnGoback = findViewById<Button>(R.id.btnGoBackFromEditJob)
        btnGoback.setOnClickListener {
            var intent = Intent(this, CompanyJobs::class.java)
            startActivity(intent)
            finish()
        }




        val edtJobtitle = findViewById<EditText>(R.id.edtEditJobTitle)
        val edtJobSal = findViewById<EditText>(R.id.edtEditJobSalary)
        val edtJobReq= findViewById<EditText>(R.id.edtEditJobDesc)
        val edtJobDesc = findViewById<EditText>(R.id.edtEditJobReq)

        GlobalScope.launch(Dispatchers.IO) {
            val data = repositoryJ.getjobDetail(jid)

            edtJobtitle.setText(data.title.toString())
            edtJobReq.setText(data.requirements)
            edtJobDesc.setText(data.description)
            edtJobSal.setText(data.salary)

        }



    }
}
