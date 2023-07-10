package com.example.appsquad

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import database.CompanyDatabase
import database.repositories.AdminRepository
import database.repositories.CompanyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch



class AdminProfile : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_profile)

        val repository = AdminRepository(CompanyDatabase.getInstance(this))

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("admin", null)

        GlobalScope.launch(Dispatchers.IO) {
            val data = cookies?.let { repository.getAdminDetail(it.toInt()) }

            val tvName = findViewById<TextView>(R.id.tvAdminEmail)
            val tvEmail = findViewById<TextView>(R.id.tvAdmnPassword)
            val imgAdmin = findViewById<ImageView>(R.id.imgAdminPic)
            if (data != null) {
                tvName.text = data.email
                tvEmail.text = data.password
                
                //converting into bitmap
                val bitmap = BitmapFactory.decodeByteArray(data.profilePic, 0, data.profilePic.size)
                imgAdmin.setImageBitmap(bitmap)
            }


        }

        val btnGoupdateAdmin =  findViewById<Button>(R.id.btnEditAdmin)
        btnGoupdateAdmin.setOnClickListener {
            var intent = Intent(this, EditAdmin::class.java)
            startActivity(intent)
            finish()
        }

        val btnLogoutAdmin = findViewById<Button>(R.id.btnLogoutAdmin)
        btnLogoutAdmin.setOnClickListener {
            val sharedPreferences = this.getSharedPreferences("MySession", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putString("admin", null)
            editor.apply()

            var intent = Intent(this, LoginChoose::class.java)

            startActivity(intent)
            finish()
        }


        val btnGoback = findViewById<Button>(R.id.btnGoMainFromAdminProfile)
        btnGoback.setOnClickListener {
            var intent = Intent(this, AdminDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}
