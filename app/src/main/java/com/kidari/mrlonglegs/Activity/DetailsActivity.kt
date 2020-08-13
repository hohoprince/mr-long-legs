package com.kidari.mrlonglegs.Activity

import android.app.Activity
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.firepush.Fire
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.kidari.mrlonglegs.R
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.activity_details.tvName

class DetailsActivity : AppCompatActivity() {

    val db = FirebaseFirestore.getInstance()
    lateinit var sbremail: String
    lateinit var sbrtoken: String
    lateinit var sbrtitle: String
    var googleUser: FirebaseUser? = null
    var sbrstate: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        googleUser = FirebaseAuth.getInstance().currentUser
        val id = intent.getStringExtra("id")
        loadData(id!!)
        Log.d("태그", "상태전")

        btn_proposal.setOnClickListener {
            if(sbremail == googleUser?.email){
                Toast.makeText(this, "내 심부름은 신청할 수 없습니다 !!", Toast.LENGTH_SHORT).show()
            }
            else {
                Fire.create()
                    .setTitle("키다리아저씨")
                    .setBody("$sbrtitle" + "  심부름이 신청되었습니다")
                    .setCallback { pushCallback, exception ->
                        //get response here
                    }
                    .toIds("$sbrtoken").push()
                Toast.makeText(this, "신청되었습니다", Toast.LENGTH_SHORT).show()
                updateErrandState(id)
                updateSupporterOfErrand(id)
                finish()
            }
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
            transaction.update(sfDocRef, "supporterName", user?.displayName)

            // Success
            null
        }.addOnSuccessListener { setResult(Activity.RESULT_FIRST_USER) }
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
                    val emergState = document["urgencyDegree"].toString()
                    tvEmerg.text = emergState
                    when (emergState) {
                        "급해요" -> ivState.setImageResource(R.drawable.ic_emerg_state_red_24)
                        "보통이에요" -> ivState.setImageResource(R.drawable.ic_emerg_state_yellow_24)
                        "널널해요" -> ivState.setImageResource(R.drawable.ic_emerg_state_green_24)
                    }
                    ivState.visibility = View.VISIBLE
                    tvTitle.text = document["title"].toString()
                    tvPayment.text = document["payment"].toString()
                    sbremail = document["email"].toString()
                    sbrtitle = document["title"].toString()
                    sbrtoken = document["token"].toString()
                    sbrstate = document["state"].toString().toInt()

                    val uri = Uri.parse(document["photoUrl"].toString())
                    if (sbrstate == 0) {
                        btn_proposal.isEnabled = true
                        Log.d("태그", "상태0")
                    } else {
                        btn_proposal.isEnabled = false
                        Log.d("태그", "상태1,2")
                    }
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