package com.kidari.mrlonglegs.Activity

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.tvName

class DetailsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val id = intent.getStringExtra("id")
        loadData(id!!)

        btn_proposal.setOnClickListener {
            Toast.makeText(this, "신청되었습니다", Toast.LENGTH_SHORT).show()
            finish()
        }
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