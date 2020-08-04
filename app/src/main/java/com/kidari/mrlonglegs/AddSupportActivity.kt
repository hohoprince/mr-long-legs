package com.kidari.mrlonglegs

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestore.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import kotlinx.android.synthetic.main.activity_add_support.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddSupportActivity : AppCompatActivity() {
    val flagOne = 1
    val flagTwo = 2
    var flag = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_support)


        val db = getInstance()
        val errand2 = Errand2()
        supNumPick_firstEndTime.minValue = 0
        supNumPick_firstEndTime.maxValue = 23
        supNumPick_secondTEndTime.minValue = 0
        supNumPick_secondTEndTime.maxValue = 59

        btn_profile.setOnClickListener {
            flag = 1
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        btn_getLicense.setOnClickListener {
            flag = 2
            //check runtime permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup to request runtime permission
                    requestPermissions(permissions, PERMISSION_CODE);
                }
                else{
                    //permission already granted
                    pickImageFromGallery();
                }
            }
            else{
                //system OS is < Marshmallow
                pickImageFromGallery();
            }
        }

        Btn_SuoRegistration.setOnClickListener {

            var supName:String = edit_supName.text.toString()
            var supPhoneNumber: String = edit_supPhoneNum.text.toString()
            var supNickname:String = edit_nickName.text.toString()
            var selectedSex :String = ""
            if (radioBtn_man.isChecked)
                selectedSex = radioBtn_man.text.toString()
            else if(radioBtn_woman.isChecked)
                selectedSex = radioBtn_woman.text.toString()
            var supTimeFrom:String = supNumPick_firstEndTime.value.toString()
            var supTimeTo:String = supNumPick_secondTEndTime.value.toString()

            var supWorkTime : String = "$supTimeFrom 부터 $supTimeTo 까지"

            val tempErrand = Errand2(supName, supPhoneNumber, supNickname, selectedSex, supWorkTime)
            db.collection("서포터").document().set(tempErrand)

        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE = 1001;
    }

    //handle requested permission result
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //handle result of picked image
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == IMAGE_PICK_CODE){
            if(flagOne == flag) {
                image_profile.setImageURI(data?.data)
                image_profile.setBackgroundColor(255)

            }else if(flagTwo == flag) {
                image_License.setImageURI(data?.data)
                image_License.setBackgroundColor(255)
            }
        }
    }
}