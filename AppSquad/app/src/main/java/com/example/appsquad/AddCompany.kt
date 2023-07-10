package com.example.appsquad

import adapters.CompanyAdapter
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import database.CompanyDatabase
import database.entities.Company
import database.repositories.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.IOException

private const val PICK_IMAGE_REQUEST = 1

class AddCompany : AppCompatActivity() {
    lateinit var cname: EditText
    lateinit var caddress: EditText
    lateinit var cemail: EditText
    lateinit var cpassword: EditText
    lateinit var cphone: EditText
    lateinit var cregno: EditText
    lateinit var cdescritpion: EditText
    lateinit var ccompanyImg: ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_company)

        cname = findViewById(R.id.edtCompanyName)
        caddress = findViewById(R.id.edtCompanyAddress)
        cemail = findViewById(R.id.edtCompanyEmail)
        cpassword = findViewById(R.id.edtCompanyPassword)
        cphone = findViewById(R.id.edtCompanyPhoneNo)
        cregno = findViewById(R.id.edtCompanyRegNo)
        cdescritpion = findViewById(R.id.edtCompanyDescription)
        ccompanyImg = findViewById(R.id.edtCompanyImg)


        val adapter = CompanyAdapter()
        val repository = CompanyRepository(CompanyDatabase.getInstance(this))

        val btnAddCompany = findViewById<Button>(R.id.btnAddComp)
        var btnGoBack = findViewById<Button>(R.id.btnGoBackfromCreateComp)
        btnAddCompany.setOnClickListener {
            addbtnClick(repository , adapter , cname , caddress , cemail , cpassword ,cphone , cregno , cdescritpion , ccompanyImg )
        }

        btnGoBack.setOnClickListener {
            var intent = Intent(this, LoginCompany::class.java)
//        intent.putExtra("answer" , ans)
            startActivity(intent)
            finish()
        }

        val btnAddImage = findViewById<ImageButton>(R.id.edtCompanyImg)
        btnAddImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            // Image picked successfully, handle the selected image here
            val selectedImageUri = data?.data
            val btnAddImage = findViewById<ImageButton>(R.id.edtCompanyImg)

            // Load the image from the URI and set it as the image resource
            try {
                val inputStream = contentResolver.openInputStream(selectedImageUri!!)
                val drawable = Drawable.createFromStream(inputStream, selectedImageUri.toString())
                btnAddImage.setImageDrawable(drawable)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            // Perform Further Operations With The Selected Image URI
        }
    }
    
    //validation
    
     fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val regexPattern = Regex("^[+]?[0-9]{10,13}\$")
        return regexPattern.matches(phoneNumber)
    }

    fun isEmailValid(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")
        return email.matches(emailPattern)
    }

    

    fun addbtnClick(repository: CompanyRepository, adapter: CompanyAdapter , cname :EditText , caddress:EditText , cemail:EditText , cpassword :EditText , cphone:EditText , cregno : EditText , cdesc:EditText , cimg:ImageButton){
        val drawable: Drawable? = cimg.drawable

        if (drawable != null && drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap

            if (bitmap != null) {
                val outputStream = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val imageByteArray = outputStream.toByteArray()

                if (isValidPhoneNumber(cphone.text.toString())) {

                    if (isEmailValid(cemail.text.toString()) ) {
                        CoroutineScope(Dispatchers.IO).launch {
                            val cm = cname.text.toString()
                            val ca = caddress.text.toString()
                            val ce = cemail.text.toString()
                            val cp = cpassword.text.toString()
                            val ap = "pending"
                            val phone = cphone.text.toString()
                            val regNo = cregno.text.toString()
                            val desc = cdesc.text.toString()

                            repository.insert(Company(cm, cp , ca ,ce , ap , regNo , phone, desc ,imageByteArray))
                            val data = repository.getAllCompanies()
                            runOnUiThread {
                                adapter.setData(data, this@AddCompany)
                            }
                        }
                        Toast.makeText(this@AddCompany, "Company Added ", Toast.LENGTH_SHORT).show()


                        var intent = Intent(this, LoginCompany::class.java)
//        intent.putExtra("answer" , ans)
                        startActivity(intent)
                        finish()

                    }else {

                        cemail.error = "Invalid"
                        Toast.makeText(this@AddCompany, "Invalid email ", Toast.LENGTH_SHORT).show()


                    }



                }else{
                    cphone.error = "Invalid"
                    Toast.makeText(this@AddCompany, "Invalid phone number", Toast.LENGTH_SHORT).show()

                }


            }
        }


//        var intent = Intent(this, LoginChoose::class.java)
////        intent.putExtra("answer" , ans)
//        startActivity(intent)
//        finish()
    }
    suspend fun showToast(message: String) = withContext(Dispatchers.Main) {
        Toast.makeText(this@AddCompany, message, Toast.LENGTH_SHORT).show()
    }

}
