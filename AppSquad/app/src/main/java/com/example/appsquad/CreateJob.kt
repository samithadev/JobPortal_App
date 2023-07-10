package com.example.appsquad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database.CompanyDatabase
import database.entities.Job
import database.repositories.CompanyRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CreateJob : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_job)

        val repository = JobRepository(CompanyDatabase.getInstance(this))

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("company", null)

        val jtitle = findViewById<EditText>(R.id.edtJobCreateTitle)
        val jsalary = findViewById<EditText>(R.id.edtCreateJobSalary)
        val jdesc =findViewById<EditText>(R.id.edtCreateJobDescritpion)
        val jreq = findViewById<EditText>(R.id.edtCreateJobRequirements)

        val btnGoback = findViewById<Button>(R.id.btnGoToCompDash)

        btnGoback.setOnClickListener {
            var intent = Intent(this, CompanyDashboard::class.java)
            startActivity(intent)
            finish()
        }
        

        val btnCreateJob = findViewById<Button>(R.id.btnAddJob)
        btnCreateJob.setOnClickListener {
            if (cookies != null) {
                addJob(repository , jtitle , jsalary , jdesc, jreq , cookies.toInt(), this)
            }
        }
    }

    fun addJob(repository: JobRepository , jtitle: EditText , jsalary:EditText , jdesc:EditText , jreq:EditText, comp:Int , context:Context){
        CoroutineScope(Dispatchers.IO).launch {
            val jt = jtitle.text.toString()
            val js = jsalary.text.toString()
            val jd = jdesc.text.toString()
            val jr = jreq.text.toString()

            val ins = repository.insert(Job(jt,js,jr , jd, comp))


                var intent = Intent(context, CompanyDashboard::class.java)
                startActivity(intent)
                finish()



        }


    }


}
