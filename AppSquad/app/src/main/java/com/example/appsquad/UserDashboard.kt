package com.example.appsquad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import database.CompanyDatabase
import database.repositories.CompanyRepository
import database.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserDashboard : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        val repository = UserRepository(CompanyDatabase.getInstance(this))

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("user", null)

        GlobalScope.launch(Dispatchers.IO) {
            val data = cookies?.let { repository.getUserDetail(it.toInt()) }

            val tvUserName = findViewById<TextView>(R.id.tvUserName)
            val tvUserEmail = findViewById<TextView>(R.id.tvUserEmail)
            val tvUserPassword = findViewById<TextView>(R.id.tvUserPassword)
            if (data != null) {
                tvUserName.text = data.name
                tvUserEmail.text = data.email
                tvUserPassword.text = data.password
            }
        }

        val btnLogout = findViewById<Button>(R.id.btnUserLogout)
        btnLogout.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("MySession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("user", null)
            editor.apply()


            var intent = Intent(this, LoginChoose::class.java)
            startActivity(intent)
            finish()
        }

        val btnGoCreateGig = findViewById<Button>(R.id.btnGoCreateGig)
        btnGoCreateGig.setOnClickListener {
            var intent = Intent(this, CreateGig::class.java)
            startActivity(intent)
            finish()
        }

        val btnUserApplications = findViewById<Button>(R.id.btnUserApplications)
        btnUserApplications.setOnClickListener {
            var intent = Intent(this, UserApplications::class.java)
            startActivity(intent)
            finish()
        }

        val btnViewMygigs = findViewById<Button>(R.id.btnViewMyGigs)
        btnViewMygigs.setOnClickListener {
            var intent = Intent(this, UserGigs::class.java)
            startActivity(intent)
            finish()
        }

        val btnViewAllJobs = findViewById<Button>(R.id.btnViewAllJobs)
        btnViewAllJobs.setOnClickListener {
            var intent = Intent(this, AllJobs::class.java)
            startActivity(intent)
            finish()
        }
    }
}