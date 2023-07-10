package com.example.appsquad

import adapters.CompanyAdapter
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import database.CompanyDatabase
import database.entities.Company
import database.repositories.CompanyRepository
import kotlinx.coroutines.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.CookieStore

private const val PICK_IMAGE_REQUEST = 1


class EditCompanyProfile : AppCompatActivity() {
    lateinit var cname: EditText
    lateinit var caddress: EditText
    lateinit var cemail: EditText
    lateinit var cpassword: EditText
    lateinit var cphone: EditText
    lateinit var creg: EditText
    lateinit var cdescription: EditText
    lateinit var clogo: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_company_profile)



        val repository = CompanyRepository(CompanyDatabase.getInstance(this))

            cname = findViewById(R.id.edtEditCompanyName)
            caddress = findViewById(R.id.edtEditCompanyAddress)
            cemail = findViewById(R.id.edtEditCompanyEmail)
            cpassword = findViewById(R.id.edtEditCompanyPassword)
            cphone = findViewById(R.id.edtEditCompanyPhone)
            cdescription = findViewById(R.id.edtEditCompanyDescription)
            creg = findViewById(R.id.edtEditCompanyReg)
            clogo = findViewById(R.id.edtEditCompanyImg)
        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("company", null)

        GlobalScope.launch(Dispatchers.IO) {
            val data = cookies?.let { repository.getCompanyDetail(it.toInt()) }
            runOnUiThread {

                val tvName = findViewById<EditText>(R.id.edtEditCompanyName)
                val tvEmail = findViewById<EditText>(R.id.edtEditCompanyEmail)
                val tvAddress = findViewById<EditText>(R.id.edtEditCompanyAddress)
                val tvPassword = findViewById<EditText>(R.id.edtEditCompanyPassword)
                val tvPhone = findViewById<EditText>(R.id.edtEditCompanyPhone)
                val tvDesc = findViewById<EditText>(R.id.edtEditCompanyDescription)
                val tvReg = findViewById<EditText>(R.id.edtEditCompanyReg)
                val btnImg = findViewById<ImageButton>(R.id.edtEditCompanyImg)
                if (data != null) {
                    tvName.setText(data.name)
                    tvEmail.setText(data.email)
                    tvAddress.setText(data.address)
                    tvPassword.setText(data.password)
                    tvReg.setText(data.regNo)
                    tvPhone.setText(data.phone)
                    tvDesc.setText(data.description)

                    val bitmap = BitmapFactory.decodeByteArray(data.companyImg, 0, data.companyImg.size)
                    btnImg.setImageBitmap(bitmap)
                }
            }


        }

        val btnGoback = findViewById<Button>(R.id.btnGoBackCompanyProfile)
        btnGoback.setOnClickListener {
            var intent = Intent(this, CompanyProfile::class.java)
            startActivity(intent)
            finish()
        }

        val btnCompImg = findViewById<ImageButton>(R.id.edtEditCompanyImg)
        btnCompImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val btnUpdate = findViewById<Button>(R.id.btnUpdateCompany)
        btnUpdate.setOnClickListener {
            if (cookies != null) {
                update(repository , cookies , cname, caddress, cemail, cpassword , cphone ,creg , cdescription ,clogo , this)


            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            // Image picked successfully, handle the selected image here
            val selectedImageUri = data?.data
            val btnAddImage = findViewById<ImageButton>(R.id.edtEditCompanyImg)

            // Load the image from the URI and set it as the image resource
            try {
                val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                val drawable = Drawable.createFromStream(inputStream, selectedImageUri.toString())
                btnAddImage.setImageDrawable(drawable)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Perform further operations with the selected image URI
        }
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val regexPattern = Regex("^[+]?[0-9]{10,13}\$")
        return regexPattern.matches(phoneNumber)
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")
        return email.matches(emailPattern)
    }

    fun update(repository: CompanyRepository, cookies: String , cname :EditText, caddress:EditText, cemail:EditText, cpassword :EditText , cphone:EditText , creg: EditText , cdes:EditText , clogo:ImageButton , context :Context) {

        CoroutineScope(Dispatchers.IO).launch {
            val data = cookies?.let { repository.getCompanyDetail(it.toInt()) }

            val id = data?.id?.toInt()
            val cm = cname.text.toString()
            val ca = caddress.text.toString()
            val ce = cemail.text.toString()
            val cp = cpassword.text.toString()
            val phn = cphone.text.toString()
            val reg = creg.text.toString()
            val des = cdes.text.toString()
            if (id != null) {

                val drawable: Drawable? = clogo.drawable

                if (drawable != null && drawable is BitmapDrawable) {
                    val bitmap: Bitmap = drawable.bitmap

                    if (bitmap != null) {
                        val outputStream = ByteArrayOutputStream()

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        val imageByteArray = outputStream.toByteArray()

                        if (isValidPhoneNumber(phn)) {

                            if (isEmailValid(ce)) {
                                repository.updateCompany(id,cm, ce , ca, cp , phn , reg, des , imageByteArray)

                                withContext(Dispatchers.Main) {
                                    val intent = Intent(context, CompanyProfile::class.java)

                                    startActivity(intent)
                                    finish()
                                }

                            }else{
                                withContext(Dispatchers.Main) {
                                    cemail.error = "Invalid email address"
                                }
                            }


                        }else{

                            withContext(Dispatchers.Main) {
                                cphone.error = "Invalid Phone Number"
                            }

                        }


                    }
                }

            }

        }
//        val btn = findViewById<Button>(R.id.btnUpdateCompany)
//        btn.text = "Updated"

//        var intent = Intent(this, CompanyProfile::class.java)
//        intent.putExtra("updated" , "yes")
//        startActivity(intent)
//        finish()
    }
}