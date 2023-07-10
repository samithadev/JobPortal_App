package com.example.appsquad

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
import database.CompanyDatabase
import database.repositories.AdminRepository
import database.repositories.CompanyRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.IOException

private const val PICK_IMAGE_REQUEST = 1

class EditAdmin : AppCompatActivity() {

    lateinit var aemail: EditText
    lateinit var apassword: EditText
    lateinit var aimage: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_admin)

        val repository = AdminRepository(CompanyDatabase.getInstance(this))
        val sharedPreferences = this.getSharedPreferences("MySession", MODE_PRIVATE)
        val cookies = sharedPreferences.getString("admin", null)

        aemail = findViewById(R.id.edtAdminEmailEdit)
        apassword = findViewById(R.id.edtAdminPasswordEdit)
        aimage = findViewById(R.id.imgAdminProfileEdit)

        GlobalScope.launch(Dispatchers.IO) {
            val data = cookies?.let { repository.getAdminDetail(it.toInt()) }
            runOnUiThread {
                val adEmail = findViewById<EditText>(R.id.edtAdminEmailEdit)
                val adPassword = findViewById<EditText>(R.id.edtAdminPasswordEdit)
                val adImage = findViewById<ImageButton>(R.id.imgAdminProfileEdit)

                if (data != null) {
                    adEmail.setText(data.email)
                    adPassword.setText(data.password)
                    val bitmap = BitmapFactory.decodeByteArray(data.profilePic, 0, data.profilePic.size)
                    adImage.setImageBitmap(bitmap)
                }
            }
        }

        val btnGoBack =  findViewById<Button>(R.id.btnGobackfromEditAdmin)
        btnGoBack.setOnClickListener {
            var intent = Intent(this, AdminProfile::class.java)
//        intent.putExtra("answer" , ans)
            startActivity(intent)
            finish()
        }

        val uploadImage = findViewById<ImageButton>(R.id.imgAdminProfileEdit)
        uploadImage.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE_REQUEST)
        }

        val btnEdit = findViewById<Button>(R.id.btnEditAdminSave)
        btnEdit.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val data = cookies?.let { repository.getAdminDetail(it.toInt()) }
                val id = data?.id?.toInt()
                val email = aemail.text.toString()
                val password = apassword.text.toString()

                val drawable: Drawable? = aimage.drawable

                if (drawable != null && drawable is BitmapDrawable) {
                    val bitmap: Bitmap = drawable.bitmap

                    if (bitmap != null) {
                        val outputStream = ByteArrayOutputStream()

                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
                        val imageByteArray = outputStream.toByteArray()

                        if (id != null) {
                            repository.updateAdmin(id ,email ,password , imageByteArray)
                        }

                    }
                }

            }
            var intent = Intent(this, AdminProfile::class.java)
//        intent.putExtra("answer" , ans)
            startActivity(intent)
            finish()
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
}