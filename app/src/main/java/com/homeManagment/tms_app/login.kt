package com.homeManagment.tms_app

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.homeManagment.tms_app.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import android.content.Intent
import com.homeManagment.tms_app.MainActivity
import com.homeManagment.tms_app.login
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.homeManagment.tms_app.AddNewReport
import android.graphics.drawable.Drawable
import android.graphics.drawable.BitmapDrawable
import android.content.ContextWrapper
import com.homeManagment.tms_app.reportview
import com.google.android.gms.tasks.OnSuccessListener
import android.app.ProgressDialog
import com.google.android.gms.tasks.OnFailureListener
import android.app.Activity
import android.provider.MediaStore
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.homeManagment.tms_app.Announcements
import org.json.JSONObject
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.homeManagment.tms_app.MyListAdapter
import android.widget.AdapterView.OnItemClickListener
import com.homeManagment.tms_app.AnnouAdapter
import java.util.*

class login : AppCompatActivity() {
    var editName: EditText? = null
    var editPassword: EditText? = null
    var result: TextView? = null
    var buttonSubmit: Button? = null
    var buttonReset: Button? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        editName = findViewById<View>(R.id.editName) as EditText
        editPassword = findViewById<View>(R.id.editPassword) as EditText
        buttonSubmit = findViewById<View>(R.id.buttonSubmit) as Button
        buttonSubmit!!.setOnClickListener {
            val email = editName!!.text.toString()
            val password = editPassword!!.text.toString()
            val db = FirebaseFirestore.getInstance()
            val user: MutableMap<String, Any> = HashMap()
            user.put("first", email)
            user.put("password", password)


            mAuth = FirebaseAuth.getInstance()
            mAuth!!.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this@login) { task ->
                    if (task.isSuccessful) {
                        val i = Intent(this@login, MainActivity::class)
                        startActivity(i)
                    } else {
                        Toast.makeText(this@login, "Invalid Username/Password ", Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, login::class.java))
        Toast.makeText(this@login, "Enter UserName/Password", Toast.LENGTH_LONG).show()
        finish()
    }
}