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

class Announcements : AppCompatActivity() {
    var list: ListView? = null
    var maintitle = arrayOf(
        "This Friday 8/04/2021, there will be a routine inspection",
        "Sunday 12/04/2021, three days left for the coming rent date",
        "This Wednesday 17/08/2021,is the last date of rent submission"
    )
    var subtitle = arrayOf(
        "4 May 2022",
        "24 April 2022",
        "11 June 2022"
    )
    var imgid = arrayOf(
        R.drawable.anccard, R.drawable.appheader,
        R.drawable.concard, R.drawable.rentcard,
        R.drawable.usericon
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_announcements)
        val adapter = AnnouAdapter(this, maintitle, subtitle)
        list = findViewById<View>(R.id.mylist) as ListView
        list!!.adapter = adapter
        val button5 = findViewById<View>(R.id.button5) as Button
        button5.setOnClickListener { finish() }
        list!!.onItemClickListener =
            OnItemClickListener { parent, view, position, id -> // TODO Auto-generated method stub
                Toast.makeText(this@Announcements, "" + subtitle[position], Toast.LENGTH_SHORT)
                    .show()
            }
    }
}