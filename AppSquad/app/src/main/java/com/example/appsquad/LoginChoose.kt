package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginChoose : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_choose)


        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookiesCompany = sharedPreferences.getString("company", null)
        val cookiesAdmin = sharedPreferences.getString("admin" , null)
        val cookiesUser = sharedPreferences.getString("user" , null)
    // redirecting according to the logged in type
    
    
     if (cookiesCompany != null) {
            var intent = Intent(this, CompanyDashboard::class.java)

            startActivity(intent)
            finish()
        }



        if (cookiesAdmin != null) {
            var intent = Intent(this, AdminDashboard::class.java)

            startActivity(intent)
            finish()
        }

        if (cookiesUser != null) {
            var intent = Intent(this, UserDashboard::class.java)

            startActivity(intent)
            finish()
        }
       

        val btnChooseUser = findViewById<Button>(R.id.btnChooseUser)
        btnChooseUser.setOnClickListener {
            var intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
            finish()
        }

        val btnChooseCompany = findViewById<Button>(R.id.btnChooseCompany)
        btnChooseCompany.setOnClickListener {
            var intent = Intent(this, LoginCompany::class.java)
            startActivity(intent)
            finish()
        }

        val btnChooseAdmin = findViewById<Button>(R.id.btnChooseAdmin)
        btnChooseAdmin.setOnClickListener {
            var intent = Intent(this, LoginAdmin::class.java)
            startActivity(intent)
            finish()
        }
    }
}
