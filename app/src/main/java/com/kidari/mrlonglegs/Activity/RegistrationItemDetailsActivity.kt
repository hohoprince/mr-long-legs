package com.kidari.mrlonglegs.Activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.firepush.Fire
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.ivDetailsProfile
import kotlinx.android.synthetic.main.activity_details.progressBar
import kotlinx.android.synthetic.main.activity_details.tvCategory
import kotlinx.android.synthetic.main.activity_details.tvContent
import kotlinx.android.synthetic.main.activity_details.tvEmerg
import kotlinx.android.synthetic.main.activity_details.tvLocation
import kotlinx.android.synthetic.main.activity_details.tvName
import kotlinx.android.synthetic.main.activity_details.tvPayment
import kotlinx.android.synthetic.main.activity_details.tvTitle
import kotlinx.android.synthetic.main.activity_registration_details.*

class RegistrationItemDetailsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var sbremail: String
    lateinit var sbrtoken: String
    lateinit var sbrtitle: String
    var sbrstate: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration_details)
        val id = intent.getStringExtra("id")
        loadData(id!!)
        Log.d("태그", "상태전")

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
                    supporter_email.text = document["supporter"].toString()
                    supporter_name.text = document["name"].toString()
                    sbremail = document["email"].toString()
                    sbrtitle = document["title"].toString()
                    sbrtoken = document["token"].toString()
                    sbrstate = document["state"].toString().toInt()
                    val uri = Uri.parse(document["photoUrl"].toString())
                    Glide.with(this).load(uri).into(ivDetailsProfile)
                    progressBar.visibility = View.GONE
                } else {
                    Log.d(TAG, "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d(TAG, "get failed with ", exception)
            }


    }

    // 심부름에 서포터 정보 입력
    fun updateErrandState(id: String) {
        val sfDocRef = db.collection("심부름").document("$id")

        db.runTransaction { transaction ->
            val snapshot = transaction.get(sfDocRef)

            // Note: this could be done without a transaction
            //       by updating the population using FieldValue.increment()
            transaction.update(sfDocRef, "state", 1)

            // Success
            null
        }.addOnSuccessListener { Log.d(TAG, "Transaction success!") }
            .addOnFailureListener { e -> Log.w(TAG, "Transaction failure.", e) }
    }
}