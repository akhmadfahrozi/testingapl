package com.example.testingapl

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.webkit.MimeTypeMap
import android.widget.Button
import android.widget.MediaController
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.testingapl.databinding.ActivityLoginGuruBinding
import com.example.testingapl.databinding.ActivityTambahVideoBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.annotations.Nullable
import com.google.firebase.storage.FirebaseStorage


class tambah_video : AppCompatActivity() {

    var uploadv: Button? = null
    var progressDialog: ProgressDialog? = null

    private val VIDEO_PICK_GALLERY_CODE = 100
    private val VIDEO_PICK_CAMERA_CODE = 101
    private val CAMERA_REQUEST_CODE = 102
    private var videoUri: Uri? = null

    private var title: String = ""
    private lateinit var progresbar: ProgressDialog
    private lateinit var cameraPermision: Array<String>


    private lateinit var bind: ActivityTambahVideoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityTambahVideoBinding.inflate(layoutInflater)
        setContentView(bind.root)



        cameraPermision =
            arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        progresbar = ProgressDialog(this)
        progresbar.setTitle("Please wait")
        progresbar.setMessage("Uploading Video...")
        progresbar.setCanceledOnTouchOutside(false)

        bind.but2.setOnClickListener {
            title = bind.nama.text.toString().trim()
            if (TextUtils.isEmpty(title)){
                Toast.makeText(this,"Title is required", Toast.LENGTH_SHORT).show()
            } else if (videoUri==null){
                Toast.makeText(this,"Pick the video first", Toast.LENGTH_SHORT).show()
            }
            else {
                uploadVideoFirebase()
            }
        }
        bind.floatingActionButton.setOnClickListener { videoPickDialog() }
    }

    private fun uploadVideoFirebase() {
        progresbar.show()
        val timestamp = ""+System.currentTimeMillis()
        val filePathAndName = "Videos/video_$timestamp"
        val storageReference = FirebaseStorage.getInstance().getReference(filePathAndName)

        storageReference.putFile(videoUri!!)
            .addOnSuccessListener { taskSnapshot ->
                val uriTask = taskSnapshot.storage.downloadUrl
                while(!uriTask.isSuccessful);
                val downloadUri = uriTask.result
                if(uriTask.isSuccessful){
                    val hashMap = HashMap<String, Any>()
                    hashMap["id"] = "$timestamp"
                    hashMap["title"] = "$title"
                    hashMap["timestamp"] = "$timestamp"
                    hashMap["videoUri"] = "$downloadUri"

                    val dbReference = FirebaseDatabase.getInstance().getReference("Videos")
                    dbReference.child(timestamp)
                        .setValue(hashMap)
                        .addOnSuccessListener { taskSnapshot ->
                            progresbar.dismiss()
                            Toast.makeText(this,"Video Uploaded", Toast.LENGTH_SHORT).show()
                        }
                        .addOnFailureListener{ e ->
                            progresbar.dismiss()
                            Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
                        }
                }
            }
            .addOnFailureListener ({ e->
                progresbar.dismiss()
                Toast.makeText(this,e.message, Toast.LENGTH_SHORT).show()
            })
    }

    private fun setVideoToVideoView() {
        val mediaController = MediaController(this)
//        mediaController.setAnchorView(bind.videoView)
        bind.videoView.setMediaController(mediaController)
        bind.videoView.setVideoURI(videoUri)
        bind.videoView.setZOrderOnTop(true)
//        bind.videoView.requestFocus()
        bind.videoView.start()
//        bind.videoView.setOnPreparedListener {
//            bind.videoView.start()
//        }
    }

    private fun videoPickDialog() {
        val options = arrayOf("Camera", "Gallery")

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Pick Video From")
            .setItems(options) { dialogInterface, i ->
                if (i == 0) {
                    if (!checkCameraPermissions()) {
                        // permission was not allowed
                        requestCameraPermissions()
                    } else {
                        // permission was allowed
                        videoPickCamera()

                    }
                } else {
                    videoPickGallery()
                }
            }
            .show()
    }

    private fun requestCameraPermissions() {
        ActivityCompat.requestPermissions(
            this,
            cameraPermision,
            CAMERA_REQUEST_CODE
        )
    }

    private fun checkCameraPermissions(): Boolean {
        val cameraPermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED

        val storagePermission = ContextCompat.checkSelfPermission(
            this,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
        return cameraPermission && storagePermission
    }

    private fun videoPickGallery() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Choose video"
            ), VIDEO_PICK_GALLERY_CODE
        )
    }

    private fun videoPickCamera() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, VIDEO_PICK_CAMERA_CODE)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE ->
                if (grantResults.size > 0) {
                    // check if permissions allowed or denied
                    val cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    if (cameraAccepted && storageAccepted) {
                        videoPickCamera()
                    } else {
                        Toast.makeText(this, "Permissions denied", Toast.LENGTH_SHORT).show()
                    }

                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            if (requestCode == VIDEO_PICK_CAMERA_CODE) {
                videoUri == data!!.data
                setVideoToVideoView()
            } else if (requestCode == VIDEO_PICK_GALLERY_CODE) {
                videoUri = data?.data
            }
        } else {
            Toast.makeText(this, "Cancelled", Toast.LENGTH_SHORT).show()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }



}