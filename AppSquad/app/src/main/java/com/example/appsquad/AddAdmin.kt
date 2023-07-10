package com.example.appsquad

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
import database.entities.Admin
import database.repositories.AdminRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException




private const val PICK_IMAGE_REQUEST = 1

class AddAdmin : AppCompatActivity() {
    lateinit var aemail: EditText
    lateinit var apassword: EditText
    lateinit var aprofile: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_admin)

        aemail = findViewById(R.id.edtAdminEmailEdit)
        apassword = findViewById(R.id.edtAdminPasswordEdit)
        aprofile = findViewById(R.id.imgAdminProfileEdit)

        val repository = AdminRepository(CompanyDatabase.getInstance(this))

        val btnCreateAdmin = findViewById<Button>(R.id.btnCreateAdmin)
        btnCreateAdmin.setOnClickListener {
            //calling the add fucntion
            addbtnClick(repository ,aemail , apassword , aprofile  )
        }

        val btnGoAdminLogin = findViewById<Button>(R.id.btnGoAdminLogin)
        btnGoAdminLogin.setOnClickListener {
            var intent = Intent(this,LoginAdmin::class.java)
            startActivity(intent)
            finish()
        }

        val btnAddImage = findViewById<ImageButton>(R.id.imgAdminProfileEdit)
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
            val btnAddImage = findViewById<ImageButton>(R.id.imgAdminProfileEdit)

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
    fun isEmailValid(email: String): Boolean {
        val emailPattern = Regex("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")
        return email.matches(emailPattern)
    }


    fun addbtnClick(repository: AdminRepository, aemail: EditText, apassword : EditText , aprofile : ImageButton){

        val drawable: Drawable? = aprofile.drawable

        if (drawable != null && drawable is BitmapDrawable) {
            val bitmap: Bitmap = drawable.bitmap

            if (bitmap != null) {
                val outputStream = ByteArrayOutputStream()

                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                val imageByteArray = outputStream.toByteArray()

                val input1 = aemail.text.toString().trim()
                val input2 = apassword.text.toString().trim()
                if (input1.isNotEmpty() &&  input2.isNotEmpty()) {

                    if (isEmailValid(aemail.text.toString()) ) {
                        CoroutineScope(Dispatchers.IO).launch {

                            val ae = aemail.text.toString()
                            val ap = apassword.text.toString()
                            repository.insert(Admin(ae,ap  , imageByteArray))
//            val data = repository.getAllAdmin()
//            runOnUiThread {
//                adapter.setData(data, this@AddCompany)
//            }
                        }
                    }else {
                        aemail.error = "Invalid Email"
                        Toast.makeText(this@AddAdmin, "Invalid email ", Toast.LENGTH_SHORT).show()

                    }

                }else {

                    Toast.makeText(this@AddAdmin, "Fill Every field", Toast.LENGTH_SHORT).show()

                }




            }
        }
//        val bitmap = BitmapFactory.decodeFile(drawable.toString()) // Replace imagePath with the actual path of your image
//        val stream = ByteArrayOutputStream()
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
//        val imageByteArray = stream.toByteArray()


        var intent = Intent(this, LoginAdmin::class.java)
//        intent.putExtra("answer" , ans)
        startActivity(intent)
        finish()
    }
}
