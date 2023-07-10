package com.example.appsquad

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import database.CompanyDatabase
import database.entities.Admin
import database.entities.User
import database.repositories.AdminRepository
import database.repositories.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddUser : AppCompatActivity() {
    lateinit var uemail: EditText
    lateinit var upassword: EditText
    lateinit var uname:EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        uemail = findViewById(R.id.edtEmailCreateUser)
        upassword = findViewById(R.id.edtPasswordCreateUser)
        uname = findViewById(R.id.edtNameCreateUser)

        val btngoback = findViewById<Button>(R.id.btnGoUserLogin)
        btngoback.setOnClickListener{
            var intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
            finish()
        }

        val repository = UserRepository(CompanyDatabase.getInstance(this))

        val btnCreateUser = findViewById<Button>(R.id.btnCreateUser)
        btnCreateUser.setOnClickListener {
            addbtnClick(repository ,uemail , upassword , uname )
        }

    }

    fun validateForm(email: String, password: String, name: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            uemail.error = "Invalid email address"
            return false
        }

        if (password.isEmpty() || password.length < 6) {
            upassword.error = "Password must be at least 6 characters long"
            return false
        }

        if (name.isEmpty()) {
            uname.error = "Name is required"
            return false
        }

        return true
    }

    fun addbtnClick(repository: UserRepository, uemail: EditText, upassword : EditText , uname :EditText){

        val email = uemail.text.toString()
        val password = upassword.text.toString()
        val name = uname.text.toString()

        if (validateForm(email, password, name)) {
            CoroutineScope(Dispatchers.IO).launch {
                repository.insert(User(email, password, name))
            }

            val intent = Intent(this, UserLogin::class.java)
            startActivity(intent)
            finish()
        }
    }


}
