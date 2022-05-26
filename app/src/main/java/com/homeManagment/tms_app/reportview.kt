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
import java.io.File

class reportview : AppCompatActivity() {
    var imageView: ImageView? = null
    var appname: TextView? = null
    var date: TextView? = null
    var condition: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reportview)


        // Toast.makeText(this, "new activty start", Toast.LENGTH_SHORT).show();
        var imageurl: String? = null
        var fur_name: String? = null
        var datess: String? = null
        var conditions: String? = null
        imageView = findViewById<View>(R.id.imageView) as ImageView
        appname = findViewById<View>(R.id.appname) as TextView
        date = findViewById<View>(R.id.date) as TextView
        condition = findViewById<View>(R.id.condition) as TextView
        val button5 = findViewById<View>(R.id.button5) as Button
        button5.setOnClickListener { finish() }
        val bundle = intent.extras
        if (bundle != null) {
            imageurl = bundle.getString("imgurl")
            fur_name = bundle.getString("furnituredetail")
            datess = bundle.getString("date")
            conditions = bundle.getString("condition")
            appname!!.text = fur_name
            date!!.text = datess
            condition!!.text = conditions
            val imgFile = File(imageurl)
            val myBitmap = BitmapFactory.decodeFile(imgFile.absolutePath)
            imageView!!.setImageBitmap(myBitmap)
        }
    }
}