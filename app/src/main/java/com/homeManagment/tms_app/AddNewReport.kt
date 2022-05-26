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
import android.net.Uri
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
import java.io.FileOutputStream
import java.io.IOException
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.*

class AddNewReport : AppCompatActivity() {
    private val btnSelect: Button? = null
    private var btnUpload: Button? = null
    var furnituredetail: EditText? = null
    private var imageView: ImageView? = null
    private var filePath: Uri? = null
    private val PICK_IMAGE_REQUEST = 22
    var bitmapsaver: Bitmap? = null
    private var mAuth: FirebaseAuth? = null

    // based on the persentage predict result
    var storage: FirebaseStorage? = null
    var storageReference: StorageReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_report)
        btnUpload = findViewById(R.id.btnUpload)
        imageView = findViewById(R.id.imgView)
        val photoButton = findViewById<View>(R.id.button1) as Button
        furnituredetail = findViewById<View>(R.id.furnituredetail) as EditText
        btnUpload.setVisibility(View.GONE)
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        photoButton.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
        val drawable = resources.getDrawable(R.drawable.camerabacktms)
        bitmapsaver = (drawable as BitmapDrawable).bitmap
        val user: MutableMap<String, Any> = HashMap()
        val Condition = arrayOf("Average", "Good", "Worst", "Poor", "Best")
        val rand = Random().nextInt(5 - 0 + 1) + 0
        val con = Condition[rand]
        val button5 = findViewById<View>(R.id.button5) as Button
        button5.setOnClickListener {
            val intentopen = Intent(applicationContext, MainActivity::class.java)
            startActivity(intentopen)
        }
        btnUpload.setOnClickListener(View.OnClickListener {
            val cw = ContextWrapper(applicationContext)
            val directory = cw.getDir("Demo", MODE_PRIVATE)
            directory.mkdir()
            val file = File(directory, System.currentTimeMillis().toString() + ".jpg")
            if (!file.exists()) {
                var fos: FileOutputStream? = null
                try {
                    fos = FileOutputStream(file)
                    bitmapsaver.compress(Bitmap.CompressFormat.JPEG, 100, fos)
                    fos.flush()
                    fos.close()
                    val imgFile = File(file.toString())
                    user.put("imgurl", file.toString())
                    user.put("furnituredetail", furnituredetail!!.text.toString())
                    val intentopen = Intent(applicationContext, reportview::class.java)
                    intentopen.putExtra("imgurl", file.toString())
                    intentopen.putExtra("furnituredetail", furnituredetail!!.text.toString())
                    intentopen.putExtra("condition", con)
                    val c = Calendar.getInstance()
                    val monthName = arrayOf(
                        "Jan", "Feb", "March", "April", "May", "June", "July",
                        "Aug", "Sep", "Oct", "Nov",
                        "Dec"
                    )
                    val month = monthName[c[Calendar.MONTH]]
                    val year = c[Calendar.YEAR]
                    val date = c[Calendar.DATE]
                    intentopen.putExtra("date", " $date $month  $year")
                    user.put("date", " $date $month  $year")
                    user.put("condition", con)
                    if (imgFile.exists()) {
                        val db = FirebaseFirestore.getInstance()
                        mAuth = FirebaseAuth.getInstance()
                        db.collection("users")
                            .add(user)
                            .addOnSuccessListener {

                                  Map<String,String> params = new HashMap<String,String>();
                                  params.put("ImageFile",imageView.getUrl().toString());
                                                            
                                RequestQueue queue = Volley.newRequestQueue(AddNewReport.this);
                                            queue.add(stringRequest);
          
                                val progressDialog = ProgressDialog(this@AddNewReport)
                                progressDialog.setMessage("Loading...") // Setting Message
                                progressDialog.setTitle("Uploading Scanning Result") // Setting Title
                                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER) // Progress Dialog Style Spinner
                                progressDialog.show() // Display Progress Dialog
                                progressDialog.setCancelable(false)
                                Thread {
                                    try {
                                        Thread.sleep(10000)
                                    } catch (e: Exception) {
                                        e.printStackTrace()
                                    }
                                    progressDialog.dismiss()
                                    startActivity(intentopen)
                                }.start()
                            }
                            .addOnFailureListener {
                                val context = applicationContext
                                val text: CharSequence = "Facing Some Error Restart App!"
                                val duration = Toast.LENGTH_LONG
                                val toast = Toast.makeText(context, text, duration)
                                toast.show()
                            }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        })
    }

    // Override onActivityResult method
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        if (requestCode == CAMERA_REQUEST) {
            val photo = data!!.extras!!["data"] as Bitmap?
            // Get the Uri of data
            bitmapsaver = photo
            imageView!!.setImageBitmap(photo)
            btnUpload!!.visibility = View.VISIBLE
        }
        super.onActivityResult(
            requestCode,
            resultCode,
            data
        )
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.data != null) {

            // Get the Uri of data
            filePath = data.data
            try {

                // Setting image on image view using Bitmap
                val bitmap = MediaStore.Images.Media
                    .getBitmap(
                        contentResolver,
                        filePath
                    )
                bitmapsaver = bitmap
                imageView!!.setImageBitmap(bitmap)
                btnUpload!!.visibility = View.VISIBLE
            } catch (e: IOException) {
                // Log the exception
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val CAMERA_REQUEST = 1888
    }
}