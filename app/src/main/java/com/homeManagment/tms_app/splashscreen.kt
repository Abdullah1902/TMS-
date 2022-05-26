package com.homeManagment.tms_app

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.homeManagment.tms_app.R
import com.google.firebase.firestore.FirebaseFirestore
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import android.content.Intent
import com.homeManagment.tms_app.MainActivity
import android.widget.Toast
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
import android.os.Handler
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.homeManagment.tms_app.Announcements
import org.json.JSONObject
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.homeManagment.tms_app.MyListAdapter
import android.widget.AdapterView.OnItemClickListener
import android.widget.AdapterView
import com.homeManagment.tms_app.AnnouAdapter

class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)
        
        
        // This method will be executed once the timer is over
        Handler().postDelayed({ 
            val i = Intent(this@splashscreen, login::class.java)
            startActivity(i)
            finish()
        }, 3000)


    }
}