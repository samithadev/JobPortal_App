package com.example.appsquad

import adapters.CompanyAdapter
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import database.CompanyDatabase
import database.repositories.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("admin", null)


        if (cookies == null) {
            var intent = Intent(this, LoginChoose::class.java)

            startActivity(intent)
            finish()
        }


        val repository = CompanyRepository(CompanyDatabase.getInstance(this))
        val recyclerView: RecyclerView = findViewById(R.id.rvCompanyList)
        val ui = this
        val adapter = CompanyAdapter()

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(ui)
        CoroutineScope(Dispatchers.IO).launch {
            val data = repository.getPendingCompanies()
            val dataCount = repository.getPendingCompanyCounts()
            val tvCount = findViewById<TextView>(R.id.tvCount)
            tvCount.text = "Pending Companies :" + dataCount.toString()

            val data1 = repository.getAllCompaniesByAddress("Kandy")
            adapter.setData(data, ui)
        }



//        val btnAddCompany = findViewById<Button>(R.id.btnAddCompany)
//        btnAddCompany.setOnClickListener {
//            displayDialog(repository , adapter)
//        }

//        val btnGoadd = findViewById<Button>(R.id.btnGoAdd)
//        btnGoadd.setOnClickListener {
//            goadd()
//        }


        val edtSearch = findViewById<EditText>(R.id.edtSearch)
        val btnSearch = findViewById<ImageButton>(R.id.btnSearch)



        val searchkey = edtSearch.text.toString()


        btnSearch.setOnClickListener{
            searchData(edtSearch.text.toString() ,repository , adapter )
        }

        val btnGoBackToAdminDash = findViewById<Button>(R.id.btnGoBacktoAdminDashboard)
        btnGoBackToAdminDash.setOnClickListener {
            var intent = Intent(this, AdminDashboard::class.java)
//        intent.putExtra("answer" , ans)
            startActivity(intent)
            finish()
        }
    }

    fun searchData(address :String, repository: CompanyRepository, adapter: CompanyAdapter){
//        val data = repository.getAllCompaniesByAddress(address)
        CoroutineScope(Dispatchers.IO).launch {
            val src = "%$address%"
            val data = repository.getAllCompaniesByAddress(src)
            runOnUiThread {
                adapter.setData(data, this@MainActivity)
            }
        }
//        adapter.setData(data , this@MainActivity)
    }

//    fun goadd(){
//        var intent = Intent(this, AddCompany::class.java)
////        intent.putExtra("answer" , ans)
//        startActivity(intent)
//        finish()
//    }

    fun displayDialog(repository: CompanyRepository, adapter: CompanyAdapter) {
        // Create a new instance of AlertDialog.Builder
        val builder = AlertDialog.Builder(this)
        // Set the alert dialog title and message
        builder.setTitle("Enter New Company item:")
        builder.setMessage("Enter the Company below:")
        // Create an EditText input field
        val name = EditText(this)
        val address = EditText(this)
        name.inputType = InputType.TYPE_CLASS_TEXT
        address.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(name)
//        builder.setView(address)
        // Set the positive button action
        builder.setPositiveButton("OK") { dialog, which ->
            // Get the input text and display a Toast message
            val cm = name.text.toString()
            val adr = address.text.toString()
            CoroutineScope(Dispatchers.IO).launch {
//                repository.insert(Company(cm,adr,))
                val data = repository.getAllCompanies()
                runOnUiThread {
                    adapter.setData(data, this@MainActivity)
                }
            }
        }
        // Set the negative button action
        builder.setNegativeButton("Cancel") { dialog, which ->
            dialog.cancel()
        }
        // Create and show the alert dialog
        val alertDialog = builder.create()
        alertDialog.show()
    }

//    val btnAddCompany = findViewById<Button>(R.id.btnAddCompany)
//    btnAddCompany.setOnClickListener {
//        displayDialog(repository)
//    }
//    btnAddCompany.setOnClickListener {
//        displayDialog()
//    }

}
