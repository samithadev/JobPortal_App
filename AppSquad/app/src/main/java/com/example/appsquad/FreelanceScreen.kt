package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Layout
import android.widget.ImageView

class FreelanceScreen : AppCompatActivity() {
    private lateinit var logo : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_freelance_screen)

        logo = findViewById(R.id.logoView)

        logo.alpha = 0f
        logo.animate().setDuration(1800).alpha(1f).withEndAction{
            val i = Intent(this,LoginChoose::class.java)
            startActivity(i)

            overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
            finish()
        }

    }
}