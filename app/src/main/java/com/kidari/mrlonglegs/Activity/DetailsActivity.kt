package com.kidari.mrlonglegs.Activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.firepush.Fire
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.tvName

class DetailsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var useremail : String
    lateinit var usertoken : String
    lateinit var usertitle : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id = intent.getStringExtra("id")
        loadData(id!!)

        btn_proposal.setOnClickListener {
            Fire.create()
                .setTitle("키다리아저씨")
                .setBody("$usertitle"+"  심부름이 신청되었습니다")
                .setCallback { pushCallback, exception ->
                    //get response here
                }
                .toIds("$usertoken").push()
            updateSupporterOfErrand(id)
            Toast.makeText(this, "신청되었습니다", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    // 심부름에 서포터 정보 입력
    fun updateSupporterOfErrand(id: String) {
        val user = FirebaseAuth.getInstance().currentUser
        val sfDocRef = db.collection("심부름").document("$id")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            transaction.update(sfDocRef, "supporter", user?.email)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }

    fun loadData(id: String) {
        val docRef = db.collection("심부름").document("$id")
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d(TAG, "DocumentSnapshot data: ${document.data}")
                    tvName.text = document["name"].toString()
                    tvCategory.text = document["category"].toString()
                    tvContent.text = document["content"].toString()
                    tvLocation.text = document["location"].toString()
                    tvEmerg.text = document["urgencyDegree"].toString()
                    tvTitle.text = document["title"].toString()
                    tvPayment.text = document["payment"].toString()
                    useremail = document["email"].toString()
                    usertitle = document["title"].toString()
                    usertoken = document["token"].toString()
                    val uri = Uri.parse(document["photoUrl"].toString())
                    Glide.with(this).load(uri).into(ivDetailsProfile)
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }
    }
}