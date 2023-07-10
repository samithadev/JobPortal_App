package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class PendingNotice : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pending_notice)
        

        val btngoback = findViewById<Button>(R.id.btnGoFromNoticeToDashboard)
        btngoback.setOnClickListener {
            var intent = Intent(this, CompanyDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }
}
