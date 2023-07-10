package com.example.appsquad

import adapters.AllGigAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.CompanyDatabase
import database.repositories.GigRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AllGigs : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_gigs)

        val recyclerView: RecyclerView = findViewById(R.id.rvAllGigs)
        val adapter = AllGigAdapter()
        val repository = GigRepository(CompanyDatabase.getInstance(this))
        val ui = this
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)


        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getAllGigs()
            adapter.setData(data , ui)

        }

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookiesAdmin = sharedPreferences.getString("admin", null)
        val cookiescompany = sharedPreferences.getString("company", null)



        val btngoback = findViewById<Button>(R.id.btnGoBackFromAllGigs)
        btngoback.setOnClickListener {

            if(cookiesAdmin != null){
                var intent = Intent(this, AdminDashboard::class.java)
                startActivity(intent)
                finish()
            }else if(cookiescompany != null){
                var intent = Intent(this, CompanyDashboard::class.java)
                startActivity(intent)
                finish()
            }



        }
    }
}