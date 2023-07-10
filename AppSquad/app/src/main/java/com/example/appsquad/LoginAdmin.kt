package com.example.appsquad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import database.CompanyDatabase
import database.repositories.AdminRepository
import database.repositories.CompanyRepository
import kotlinx.coroutines.*

class LoginAdmin : AppCompatActivity() {

    lateinit var aemail: EditText
    lateinit var apassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_admin)

        aemail = findViewById(R.id.edtEmailLoginUser)
        apassword = findViewById(R.id.edtPasswordLoginUser)

//        val adapter = CompanyAdapter()
        val repository = CompanyRepository(CompanyDatabase.getInstance(this))
        val repository1 = AdminRepository(CompanyDatabase.getInstance(this))

        val btnGoChooseLogin =  findViewById<Button>(R.id.btnGoChooseLoginAdmin)
        btnGoChooseLogin.setOnClickListener {
            var intent = Intent(this, LoginChoose::class.java)
            startActivity(intent)
            finish()
        }

        val btnLoginAdmin = findViewById<Button>(R.id.btnLoginAdmin)
        btnLoginAdmin.setOnClickListener {
            loginAdmin(repository1 , aemail , apassword , this)
        }

        val btnGoCreateAdmin = findViewById<Button>(R.id.btnGoCreateAdmin)
        btnGoCreateAdmin.setOnClickListener {
            var intent = Intent(this, AddAdmin::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun loginAdmin(repository: AdminRepository, ademail: EditText, adpassword: EditText, context: Context){
//        var data:Company?
        val ae = ademail.text.toString()
        val ap = adpassword.text.toString()

        val userInput1 = ademail.text.toString().trim()
        val userInput2 = adpassword.text.toString().trim()
        if (userInput1.isNotEmpty() &&  userInput2.isNotEmpty()) {

            val scope = CoroutineScope(Dispatchers.Main)
            GlobalScope.launch(Dispatchers.IO) {
            val data = repository.getAdminLogin(ae)

            if (data != null) {
                if (data.password == ap) {

                    val sharedPreferences =
                        context.getSharedPreferences("MySession", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("admin", data.id.toString())
                    editor.apply()

                    var intent = Intent(context, AdminDashboard::class.java)
                    startActivity(intent)
                    finish()

                } else {
                    showToast("Incorrect Password")
                }
            } else {
                showToast("Incorrect Email")

            }

        }
        }else {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
        }



    }

    suspend fun showToast(message: String) = withContext(Dispatchers.Main) {
        Toast.makeText(this@LoginAdmin, message, Toast.LENGTH_SHORT).show()
    }
}