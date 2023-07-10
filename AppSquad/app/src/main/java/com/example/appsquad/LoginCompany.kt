package com.example.appsquad

import adapters.CompanyAdapter
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database.CompanyDatabase
import database.entities.Company
import database.repositories.CompanyRepository
import kotlinx.coroutines.*



//import kotlin.coroutines.jvm.internal.CompletedContinuation.context

class LoginCompany : AppCompatActivity() {

    lateinit var cemail: EditText
    lateinit var cpassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_company)

        cemail = findViewById(R.id.edtCompanyLoginEmail)
        cpassword = findViewById(R.id.edtCompanyLoginPassword)

        val adapter = CompanyAdapter()
        val repository = CompanyRepository(CompanyDatabase.getInstance(this))

        val btnLoginCompany = findViewById<Button>(R.id.btnCompanyLogin)
        btnLoginCompany.setOnClickListener{
            loginCompany(repository , adapter , cemail , cpassword , this )
        }

        //btn login
        val btnGoChooseLogin = findViewById<Button>(R.id.btnGoChooseLogin)
        btnGoChooseLogin.setOnClickListener {
            var intent = Intent(this, LoginChoose::class.java)
            startActivity(intent)
            finish()
        }
        

        val btnGoCreateCompany =  findViewById<Button>(R.id.btnCompanyCreate)
        btnGoCreateCompany.setOnClickListener {
            var intent = Intent(this, AddCompany::class.java)
            startActivity(intent)
            finish()
        }
    }


    fun loginCompany(repository: CompanyRepository , adapter: CompanyAdapter , clemail: EditText , clpassword:EditText , context: Context ){
//        var data:Company?
        val ce = clemail.text.toString()
        val cp = clpassword.text.toString()

        val input1 = clemail.text.toString().trim()
        val input2 =clpassword.text.toString().trim()
        if (input1.isNotEmpty() &&  input2.isNotEmpty()) {

            CoroutineScope(Dispatchers.IO).launch {
                val data = repository.getCompanyLogin(ce)

                if( data != null) {
                    if( data.password == cp ) {

                        val sharedPreferences = context.getSharedPreferences("MySession", Context.MODE_PRIVATE)
                        val editor = sharedPreferences.edit()
                        editor.putString("company", data.id.toString())
                        editor.apply()

                        var intent = Intent(context, CompanyDashboard::class.java)
                        startActivity(intent)
                        finish()

                    }else {
                        withContext(Dispatchers.Main) {
                            clpassword.error = "Invalid Password"
                        }
//                            Toast.makeText(context, "Invalid password", Toast.LENGTH_SHORT).show()

                    }

                }else {
                    withContext(Dispatchers.Main) {
                        clemail.error = "Invalid email Address"
                    }
//                    Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()

                }


            }


        }else {
            clemail.error = "Required"
            clpassword.error = "Required"
            Toast.makeText(this@LoginCompany, "Fill all the fields", Toast.LENGTH_SHORT).show()

        }


//        CoroutineScope(Dispatchers.IO).launch {
//            val data = repository.getCompanyLogin(ce)
//            if( data.password == cp ) {
//
//            }
//        }
//            if( data.password == cp ) {
//                var intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//                finish()
//            }else {
//                var intent = Intent(this, AddCompany::class.java)
////        intent.putExtra("answer" , ans)
//                startActivity(intent)
//                finish()
//            }



    }
 suspend fun showToast(message: String) = withContext(Dispatchers.Main) {
        Toast.makeText(this@LoginCompany, message, Toast.LENGTH_SHORT).show()
    }
   


}
