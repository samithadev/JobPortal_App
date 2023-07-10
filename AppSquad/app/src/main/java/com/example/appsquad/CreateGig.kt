package com.example.appsquad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import database.CompanyDatabase
import database.entities.Gig
import database.repositories.GigRepository
import database.repositories.JobRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.Toast

class CreateGig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_gig)

        val repository = GigRepository(CompanyDatabase.getInstance(this))

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("user", null)

        val btnGoback = findViewById<Button>(R.id.btnGoBacktoUserDash)
        btnGoback.setOnClickListener {
            var intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
            finish()
        }

        val gtitle = findViewById<EditText>(R.id.edtGigTitle)
        val gdesc = findViewById<EditText>(R.id.edtGigDescription)
        val gprice =findViewById<EditText>(R.id.edtGigPrice)

        val btnCreateGig = findViewById<Button>(R.id.btnCreateGig)
        btnCreateGig.setOnClickListener {
            if (cookies != null) {
                addJob(repository , gtitle , gdesc , gprice ,cookies.toInt(), this)
            }
        }

    }


    fun addJob(repository: GigRepository, gtitle: EditText, gdesc:EditText, gprice:EditText, user:Int, context: Context){

        val title = gtitle.text.toString()
        val description = gdesc.text.toString()
        val price = gprice.text.toString()

        fun validateForm(title: String, description: String, price: String): Boolean {
            if (title.isEmpty()) {
                gtitle.error = "Title is required"
                return false
            }

            if (description.isEmpty()) {
                gdesc.error = "Description is required"
                return false
            }

            if (price.isEmpty()) {
                gprice.error = "Price is required"
                return false
            }

            return true
        }

        if (validateForm(title, description, price)) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(Gig(title, description, price, user))

                // Show toast message for successful gig addition
                launch(Dispatchers.Main) {
                    Toast.makeText(context, "Gig added successfully", Toast.LENGTH_SHORT).show()
                }

                val intent = Intent(context, UserDashboard::class.java)
                startActivity(intent)
                finish()
            }
        }

    }
}