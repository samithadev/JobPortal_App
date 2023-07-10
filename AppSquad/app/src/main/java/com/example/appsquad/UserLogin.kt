package com.example.appsquad

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import database.CompanyDatabase
import database.repositories.AdminRepository
import database.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserLogin : AppCompatActivity() {
    lateinit var uemail: EditText
    lateinit var upassword: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        uemail =findViewById(R.id.edtEmailLoginUser)
        upassword = findViewById(R.id.edtPasswordLoginUser)

        val btnGoback = findViewById<Button>(R.id.btnGoChooseLoginUser)
        btnGoback.setOnClickListener {
            var intent = Intent(this, LoginChoose::class.java)
            startActivity(intent)
            finish()
        }

        val btnCreateUser = findViewById<Button>(R.id.btnGoCreateUser)
        btnCreateUser.setOnClickListener {
            var intent = Intent(this, AddUser::class.java)
            startActivity(intent)
            finish()
        }
        val repository1 = UserRepository(CompanyDatabase.getInstance(this))
        val btnUserLogin = findViewById<Button>(R.id.btnLoginUser)
        btnUserLogin.setOnClickListener {
            loginUSer(repository1 , uemail , upassword , this)
        }
    }

    fun loginUSer(repository: UserRepository, uemail: EditText, upassword: EditText, context: Context){
//        var data:Company?
        val ue = uemail.text.toString()
        val up = upassword.text.toString()

        GlobalScope.launch(Dispatchers.IO) {
            val data = repository.getUserLogin(ue)

            if ( data!= null) {
                if( data.password == up ) {

                    val sharedPreferences = context.getSharedPreferences("MySession", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("user", data.id.toString())
                    editor.apply()

                    var intent = Intent(context, UserDashboard::class.java)
                    startActivity(intent)
                    finish()

                }else {
                    var intent = Intent(context, LoginChoose::class.java)
                    startActivity(intent)
                    finish()
                }
            }else {
                var intent = Intent(context, AddUser::class.java)
                startActivity(intent)
                finish()
            }


        }



    }
}