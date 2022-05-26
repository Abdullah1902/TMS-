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
import android.util.Log
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

class MainActivity : AppCompatActivity() {
    var list: ListView? = null
    var annoucementtab: LinearLayout? = null
    val  maintitle = ArrayList<String>()
    var subtitle = ArrayList<String>()
    var ImageAddress = ArrayList<String>()
    var conditionAddress = ArrayList<String>()
    var NotificationCountShow: TextView? = null
    var NotificationCount = Random().nextInt(16 - 5 + 1) + 5
    var addnewreportbtn: Button? = null
    var btn1: Button? = null
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        NotificationCountShow = findViewById(R.id.noticount)
        addnewreportbtn = findViewById(R.id.addnewreport)
        annoucementtab = findViewById(R.id.annoucementtab)
        btn1 = findViewById(R.id.button3)
        btn1.setOnClickListener(View.OnClickListener { // This method will be executed once the timer is over
            val la = Intent(this@MainActivity, login::class.java)
            startActivity(la)
        })
        annoucementtab.setOnClickListener(View.OnClickListener { // This method will be executed once the timer is over
            val ip = Intent(this@MainActivity, Announcements::class.java)
            startActivity(ip)
        })
        addnewreportbtn.setOnClickListener(View.OnClickListener { // This method will be executed once the timer is over
            val ip = Intent(this@MainActivity, AddNewReport::class.java)
            startActivity(ip)
        })
        NotificationCountShow.setText("" + NotificationCount)
        val db = FirebaseFirestore.getInstance()
        mAuth = FirebaseAuth.getInstance()
        val obj = JSONObject()
        list = findViewById<View>(R.id.list) as ListView
        db.collection("users")
            .get()
            .addOnCompleteListener(this@MainActivity) { task ->
                if (task.isSuccessful) {
                    val indexValue = 0
                    for (document in task.result) {
                        maintitle.add(document.data["furnituredetail"].toString())
                        subtitle.add(document.data["date"].toString())
                        ImageAddress.add(document.data["imgurl"].toString())
                        conditionAddress.add(document.data["condition"].toString())
                        val adapter = MyListAdapter(this@MainActivity, maintitle, subtitle)
                        list!!.adapter = adapter
                    }
                } else {
                    Log.w("result", "Error getting documents.", task.exception)
                }
            }
        list!!.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            // TODO Auto-generated method stub
            val intentopen = Intent(applicationContext, reportview::class.java)
            intentopen.putExtra("imgurl", ImageAddress[position])
            intentopen.putExtra("furnituredetail", maintitle[position])
            intentopen.putExtra("condition", conditionAddress[position])
            intentopen.putExtra("date", subtitle[position])
            startActivity(intentopen)
            //                Toast.makeText(MainActivity.this, " "+maintitle.get(position), Toast.LENGTH_SHORT).show();
        }
    }
}