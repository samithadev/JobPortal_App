package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database.CompanyDatabase
import database.repositories.ApplicationRepository
import database.repositories.GigRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditApplication : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_application)

        val id = intent.getStringExtra("aplId").toString()
        val aid = id.toInt()
        val repository = ApplicationRepository(CompanyDatabase.getInstance(this))

        val notes = findViewById<EditText>(R.id.edtApplNotes)
        val contact = findViewById<EditText>(R.id.edtApplContact)

        GlobalScope.launch(Dispatchers.IO) {
            val data = repository.getApplDetails(aid)

            runOnUiThread {


                notes.setText(data.notes)
                contact.setText(data.contact)
            }
        }

        val btnGoback = findViewById<Button>(R.id.btnGoBackFromEditAppl)
        btnGoback.setOnClickListener {
            var intent = Intent(this, UserApplications::class.java)
            startActivity(intent)
            finish()
        }

        val btnSave = findViewById<Button>(R.id.btnSaveAppl)
        btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val nts = notes.text.toString()
                val cnt = contact.text.toString()
                repository.updateAppl(aid , nts , cnt)



            }

            Toast.makeText(this@EditApplication, "Application Updated", Toast.LENGTH_SHORT).show()

            var intent = Intent(this, UserApplications::class.java)
            startActivity(intent)
            finish()
        }
    }
}