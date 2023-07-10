package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import database.CompanyDatabase
import database.repositories.CompanyRepository
import database.repositories.GigRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditGig : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_gig)

        val repository = GigRepository(CompanyDatabase.getInstance(this))

        val btngoback = findViewById<Button>(R.id.btnGoBackFromEditGig)
        btngoback.setOnClickListener {
            var intent = Intent(this, UserGigs::class.java)
            startActivity(intent)
            finish()
        }
        val id = intent.getStringExtra("gigId").toString()
        val gid = id.toInt()

        val edtGTitle = findViewById<EditText>(R.id.edtEditGigTitle)
        val edtGDesc = findViewById<EditText>(R.id.edtEditGigDesc)
        val edtGPrice = findViewById<EditText>(R.id.edtEditGigPrice)
        val btnsaveGig = findViewById<Button>(R.id.btnEditSaveGig)
        btnsaveGig.setOnClickListener {
            updateGig(repository, edtGTitle, edtGDesc ,edtGPrice, gid )
        }




        GlobalScope.launch(Dispatchers.IO) {

            val data = repository.getgigDetails(gid)
            runOnUiThread {
                val edtGTitle = findViewById<EditText>(R.id.edtEditGigTitle)
                val edtGDesc = findViewById<EditText>(R.id.edtEditGigDesc)
                val edtGPrice = findViewById<EditText>(R.id.edtEditGigPrice)

                edtGTitle.setText(data.title)
                edtGDesc.setText(data.description)
                edtGPrice.setText(data.price)
            }
        }

    }

    fun updateGig(repository: GigRepository , edtGTitle:EditText ,edtGDesc:EditText , edtGPrice:EditText , id:Int){
        CoroutineScope(Dispatchers.IO).launch {

            val title = edtGTitle.text.toString()
            val description = edtGDesc.text.toString()
            val price = edtGPrice.text.toString()

            repository.updateGig(title, description, price, id)

            // Show toast message for successful gig update
            launch(Dispatchers.Main) {
                Toast.makeText(this@EditGig, "Gig updated successfully", Toast.LENGTH_SHORT).show()
            }
        }

        var intent = Intent(this, UserGigs::class.java)
        startActivity(intent)
        finish()
    }
}
